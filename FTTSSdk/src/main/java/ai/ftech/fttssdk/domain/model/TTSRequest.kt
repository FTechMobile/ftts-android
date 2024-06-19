package ai.ftech.fttssdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TTSRequest(
    @SerialName("language")
    var language: String? = null,
    @SerialName("voice")
    var voice: String? = null,
    @SerialName("content")
    var content: String? = null,
)