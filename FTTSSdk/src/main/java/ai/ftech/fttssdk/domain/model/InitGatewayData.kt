package ai.ftech.fttssdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InitGatewayData(
    @SerialName("token")
    var token: String? = null
)