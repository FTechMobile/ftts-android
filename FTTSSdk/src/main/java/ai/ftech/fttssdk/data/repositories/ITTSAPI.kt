package ai.ftech.fttssdk.data.repositories

import ai.ftech.fttssdk.domain.model.LanguageConfigResponse
import ai.ftech.fttssdk.domain.model.TTSRequest
import ai.ftech.fttssdk.domain.model.TTSResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ITTSAPI {
    @POST("tts/transform")
    suspend fun tts(@Body request: TTSRequest): TTSResponse

    @Multipart
    @POST("tts/transform-file")
    suspend fun tts(@Part file: MultipartBody.Part, @Part("language") language: RequestBody, @Part("voice") voice: RequestBody): TTSResponse

    @GET("tts/language-config")
    suspend fun getLanguageConfig(): LanguageConfigResponse
}