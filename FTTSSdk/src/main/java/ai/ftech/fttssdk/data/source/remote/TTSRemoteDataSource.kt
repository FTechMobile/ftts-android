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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TTSRemoteDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    private val sttAPI: ITTSAPI,
) : TTSDataSource {

    override fun tts(request: TTSRequest): Flow<BaseCallBack<TTSResponse>> {
        return flow {
            val result = sttAPI.runCatching { stt(request) }
            emit(result.handle())
        }
    }

    override fun getLanguageConfig(): Flow<BaseCallBack<LanguageConfigResponse>> {
        return flow {
            val result = sttAPI.runCatching { getLanguageConfig() }
            emit(result.handle())
        }
    }
}