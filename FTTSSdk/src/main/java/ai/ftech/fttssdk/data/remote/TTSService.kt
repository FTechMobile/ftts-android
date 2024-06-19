package ai.ftech.fttssdk.data.remote

import ai.ftech.fttssdk.data.local.IDataStore
import ai.ftech.fttssdk.utils.AppConstant
import ai.ftech.fttssdk.utils.DataStoreConstant
import kotlinx.coroutines.runBlocking
import okhttp3.logging.HttpLoggingInterceptor

class TTSService (private val datastore: IDataStore) : NetworkServices() {
    override var baseUrl: String = AppConstant.BASE_TTS_URL
    override val headers: Map<String, String>
        get() = mapOf(
            AppConstant.HEADER_CONTENT_TYPE to AppConstant.APPLICATION_JSON,
            AppConstant.HEADER_AUTHORIZATION to runBlocking {
                String.format(AppConstant.AUTH_BEARER, datastore.get(DataStoreConstant.TOKEN_KEY, ""))
            }
        )

    override var timeout: Long = AppConstant.TIMEOUT

    override var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY

    init {
        build()
    }
}