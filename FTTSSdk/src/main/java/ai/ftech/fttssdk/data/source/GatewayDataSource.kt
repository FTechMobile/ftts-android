package ai.ftech.fttssdk.data.source

import ai.ftech.fttssdk.domain.model.BaseCallBack
import ai.ftech.fttssdk.domain.model.InitGatewayRequest
import ai.ftech.fttssdk.domain.model.InitGatewayResponse
import kotlinx.coroutines.flow.Flow

interface GatewayDataSource {
    fun initGateway(request: InitGatewayRequest): Flow<BaseCallBack<InitGatewayResponse>>
}