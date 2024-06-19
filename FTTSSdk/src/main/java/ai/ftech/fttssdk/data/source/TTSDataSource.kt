package ai.ftech.fttssdk.data.source

import ai.ftech.fttssdk.domain.model.BaseCallBack
import ai.ftech.fttssdk.domain.model.LanguageConfigResponse
import ai.ftech.fttssdk.domain.model.TTSRequest
import ai.ftech.fttssdk.domain.model.TTSResponse
import kotlinx.coroutines.flow.Flow

interface TTSDataSource {
    fun tts(request: TTSRequest): Flow<BaseCallBack<TTSResponse>>
    fun getLanguageConfig(): Flow<BaseCallBack<LanguageConfigResponse>>
}