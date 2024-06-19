package ai.ftech.fttssdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TTSResponse(
    @SerialName("data")
    var data: TTSData? = null
) : BaseResponse() {
    override fun isSuccessful(): Boolean {
        return code == 0 && data != null
    }
}