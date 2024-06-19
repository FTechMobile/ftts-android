package ai.ftech.fttssdk.data.repositories

import ai.ftech.fttssdk.domain.model.LanguageConfigResponse
import ai.ftech.fttssdk.domain.model.TTSRequest
import ai.ftech.fttssdk.domain.model.TTSResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ITTSAPI {
    @POST("tts/transform")
    suspend fun stt(@Body request: TTSRequest): TTSResponse

    @GET("tts/language-config")
    suspend fun getLanguageConfig(): LanguageConfigResponse
}