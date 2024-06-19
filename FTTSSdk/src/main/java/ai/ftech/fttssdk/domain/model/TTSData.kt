package ai.ftech.fttssdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TTSData(
    @SerialName("audio_url")
    var audioUrl: String? = null
)