package ai.ftech.fttssdk.data.repositories

import ai.ftech.fttssdk.data.source.TTSDataSource
import ai.ftech.fttssdk.domain.model.BaseCallBack
import ai.ftech.fttssdk.domain.model.LanguageConfigResponse
import ai.ftech.fttssdk.domain.model.TTSRequest
import ai.ftech.fttssdk.domain.model.TTSResponse
import ai.ftech.fttssdk.domain.repositories.TTSRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class STTRepoImpl @Inject constructor(private val TTSDataSource: TTSDataSource) : TTSRepo {
    override fun tts(request: TTSRequest): Flow<BaseCallBack<TTSResponse>> {
        return TTSDataSource.tts(request)
    }

    override fun getLanguageConfig(): Flow<BaseCallBack<LanguageConfigResponse>> {
        return TTSDataSource.getLanguageConfig()
    }

}