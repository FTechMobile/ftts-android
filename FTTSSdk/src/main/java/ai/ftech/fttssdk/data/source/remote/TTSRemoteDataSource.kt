package ai.ftech.fttssdk.data.source.remote

import ai.ftech.fttssdk.data.repositories.ITTSAPI
import ai.ftech.fttssdk.data.source.TTSDataSource
import ai.ftech.fttssdk.data.source.handle
import ai.ftech.fttssdk.domain.model.BaseCallBack
import ai.ftech.fttssdk.domain.model.LanguageConfigResponse
import ai.ftech.fttssdk.domain.model.TTSRequest
import ai.ftech.fttssdk.domain.model.TTSResponse
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TTSRemoteDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    private val ttsAPI: ITTSAPI,
) : TTSDataSource {

    companion object {
        private const val PART_FILE = "file"
    }

    override fun tts(request: TTSRequest): Flow<BaseCallBack<TTSResponse>> {
        return flow {
            val result = ttsAPI.runCatching { tts(request) }
            emit(result.handle())
        }
    }

    override fun tts(request: TTSRequest, filePath: String): Flow<BaseCallBack<TTSResponse>> {
        return flow {
            val part = convertFileToMultipart(filePath)
            val language = (request.language?:"").toRequestBody("text/plain".toMediaTypeOrNull())
            val voice = (request.voice?:"").toRequestBody("text/plain".toMediaTypeOrNull())
            val result = ttsAPI.runCatching { tts(part,language,voice) }
            emit(result.handle())
        }
    }

    override fun getLanguageConfig(): Flow<BaseCallBack<LanguageConfigResponse>> {
        return flow {
            val result = ttsAPI.runCatching { getLanguageConfig() }
            emit(result.handle())
        }
    }

    private fun convertFileToMultipart(absolutePath: String): MultipartBody.Part {
        val file = File(absolutePath)
        return MultipartBody.Part.createFormData(PART_FILE, file.name, file.asRequestBody())
    }
}