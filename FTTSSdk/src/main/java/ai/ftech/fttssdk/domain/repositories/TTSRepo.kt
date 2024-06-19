package ai.ftech.fttssdk.domain.repositories

import ai.ftech.fttssdk.domain.model.BaseCallBack
import ai.ftech.fttssdk.domain.model.LanguageConfigResponse
import ai.ftech.fttssdk.domain.model.TTSRequest
import ai.ftech.fttssdk.domain.model.TTSResponse
import kotlinx.coroutines.flow.Flow

interface TTSRepo {
    fun tts(request:TTSRequest): Flow<BaseCallBack<TTSResponse>>
    fun getLanguageConfig(): Flow<BaseCallBack<LanguageConfigResponse>>
}