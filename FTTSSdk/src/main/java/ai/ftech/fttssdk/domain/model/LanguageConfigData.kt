package ai.ftech.fttssdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LanguageConfigData(
    @SerialName("config_id")
    var configId: String? = null,
    @SerialName("name")
    var name: String? = null,
    @SerialName("voice")
    val listVoice: List<LanguageVoice> = listOf()
)

@Serializable
data class LanguageVoice(
    @SerialName("config_id")
    var configId: String? = null,
    @SerialName("name")
    var name: String? = null
)