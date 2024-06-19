package ai.ftech.fttssdk.domain.repositories

import ai.ftech.fttssdk.domain.model.BaseCallBack
import ai.ftech.fttssdk.domain.model.InitGatewayRequest
import ai.ftech.fttssdk.domain.model.InitGatewayResponse
import kotlinx.coroutines.flow.Flow

interface GatewayRepo {
    fun initGateway(request: InitGatewayRequest): Flow<BaseCallBack<InitGatewayResponse>>
}