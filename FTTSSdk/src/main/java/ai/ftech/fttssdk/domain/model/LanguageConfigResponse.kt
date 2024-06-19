package ai.ftech.fttssdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LanguageConfigResponse(
    @SerialName("data")
    var data: List<LanguageConfigData> = listOf()
) : BaseResponse() {
    override fun isSuccessful(): Boolean {
        return code == 0 && data.isNotEmpty()
    }
}