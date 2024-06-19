package ai.ftech.fttssdk.sdk

import ai.ftech.fttssdk.R
import ai.ftech.fttssdk.extension.getAppString
import ai.ftech.fttssdk.extension.getString
import ai.ftech.fttssdk.data.local.DataStoreService
import ai.ftech.fttssdk.data.local.IDataStore
import ai.ftech.fttssdk.data.remote.GatewayService
import ai.ftech.fttssdk.data.remote.TTSService
import ai.ftech.fttssdk.data.repositories.IGatewayAPI
import ai.ftech.fttssdk.data.repositories.ITTSAPI
import ai.ftech.fttssdk.data.source.remote.GatewayRemoteDataSource
import ai.ftech.fttssdk.data.source.remote.TTSRemoteDataSource
import ai.ftech.fttssdk.domain.exceptions.AppException
import ai.ftech.fttssdk.domain.model.BaseCallBack
import ai.ftech.fttssdk.domain.model.InitGatewayRequest
import ai.ftech.fttssdk.domain.model.TTSRequest
import ai.ftech.fttssdk.utils.AppConstant
import ai.ftech.fttssdk.utils.DataStoreConstant
import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object TTSManager {
    private val TAG: String = TTSManager::class.java.simpleName
    private var applicationContext: Context? = null
    private var gateWayRemote: GatewayRemoteDataSource? = null
    private var ttsRemote: TTSRemoteDataSource? = null
    private var dataStore: IDataStore? = null
    private var sttCallback: ISTTCallback? = null
    private var player: MediaPlayer? = null
    private var audioUrl:String?=null


    @JvmStatic
    fun init(context: Context) {
        applicationContext = context
        dataStore = DataStoreService(context)
        gateWayRemote = GatewayRemoteDataSource(
            context,
            GatewayService().create(IGatewayAPI::class.java))
        ttsRemote = TTSRemoteDataSource(context, TTSService(dataStore as IDataStore).create(ITTSAPI::class.java))
        runBlocking {
            dataStore?.remove(DataStoreConstant.TOKEN_KEY)
        }
    }

    fun getApplicationContext(): Application {
        return applicationContext as? Application
            ?: throw RuntimeException(getAppString(R.string.context_null_error))
    }

    @JvmStatic
    fun initGateway(appId: String, secretKey: String, callback: IInitGatewayCallback) {
        if (appId.isEmpty()) {
            callback.onFail(
                AppException(
                    statusCode = AppConstant.UNKNOWN_ERROR,
                    message = getAppString(R.string.empty_app_id)
                )
            )
            return
        }
        if (secretKey.isEmpty()) {
            callback.onFail(
                AppException(
                    statusCode = AppConstant.UNKNOWN_ERROR,
                    message = getAppString(R.string.empty_secret_key)
                )
            )
            return
        }
        val request = InitGatewayRequest().apply {
            this.appId = appId
            this.secretKey = secretKey
        }
        gateWayRemote?.let { remote ->
            CoroutineScope(Dispatchers.IO).launch {
                remote.initGateway(request).collect { result ->
                    when (result) {
                        is BaseCallBack.Success -> {
                            val token = result.data?.data?.token.getString()
                            if (token.isEmpty()) {
                                callback.onFail(AppException(getAppString(R.string.message_token_is_empty)))
                            } else {
                                dataStore?.put(DataStoreConstant.TOKEN_KEY, result.data?.data?.token.getString())
                                callback.onSuccess()
                            }
                        }

                        is BaseCallBack.Error -> {
                            callback.onFail(result.error)
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    fun getLanguageConfig(callback: ILanguageConfigCallback) {
        ttsRemote?.let { remote ->
            CoroutineScope(Dispatchers.IO).launch {
                remote.getLanguageConfig().collect { result ->
                    when (result) {
                        is BaseCallBack.Success -> {
                            callback.onSuccess(result.data?.data ?: listOf())
                        }

                        is BaseCallBack.Error -> {
                            callback.onFail(result.error)
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    fun registerTTSCallback(sttCallback: ISTTCallback) {
        this.sttCallback = sttCallback
    }

    @JvmStatic
    fun startTTS(language: String, voice: String, content: String, isAutoPlayAudio: Boolean) {
        if (isNotInitGateway()) {
            sttCallback?.onFail(AppException(getAppString(R.string.message_recorder_not_initial)))
            return
        }

        if (language.isEmpty()) {
            sttCallback?.onFail(AppException(getAppString(R.string.message_language_empty)))
            return
        }

        if (voice.isEmpty()) {
            sttCallback?.onFail(AppException(getAppString(R.string.message_voice_empty)))
            return
        }

        if (content.isEmpty()) {
            sttCallback?.onFail(AppException(getAppString(R.string.message_content_empty)))
            return
        }
        sttCallback?.onStart()

        val request = TTSRequest().apply {
            this.language = language
            this.voice = voice
            this.content = content
        }
        ttsRemote?.let { remote ->
            CoroutineScope(Dispatchers.IO).launch {
                remote.tts(request).collect { result ->
                    when (result) {
                        is BaseCallBack.Success -> {
                            audioUrl = result.data?.data?.audioUrl
                            if (isAutoPlayAudio) {
                                startPlaying()
                            }else{
                                sttCallback?.onSuccess(audioUrl.getString())
                            }

                        }

                        is BaseCallBack.Error -> {
                            sttCallback?.onFail(result.error)
                        }
                    }
                }
            }
        }
    }

    private fun isNotInitGateway(): Boolean {
        return runBlocking {
            val token = dataStore?.get(DataStoreConstant.TOKEN_KEY, "")?.getString()
            token.isNullOrEmpty()
        }
    }

    private fun startPlaying() {
        stopPlaying()
        sttCallback?.onPlaying()
        player = MediaPlayer().apply {
            try {
                setDataSource(audioUrl)
                prepare()
                start()
            } catch (e: Exception) {
                e.printStackTrace()
                sttCallback?.onFail(AppException(e.message))
            }
        }
        player?.setOnCompletionListener {
            stopPlaying()
            sttCallback?.onSuccess(audioUrl.getString())
        }
    }

    private fun stopPlaying() {
        if (player?.isPlaying == true) {
            player?.stop()
        }
        player?.release()
        player = null
    }
}