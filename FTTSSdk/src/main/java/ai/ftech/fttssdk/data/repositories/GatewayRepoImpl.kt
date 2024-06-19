package ai.ftech.fttssdk.data.repositories

import ai.ftech.fttssdk.data.source.GatewayDataSource
import ai.ftech.fttssdk.domain.model.BaseCallBack
import ai.ftech.fttssdk.domain.model.InitGatewayRequest
import ai.ftech.fttssdk.domain.model.InitGatewayResponse
import ai.ftech.fttssdk.domain.repositories.GatewayRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GatewayRepoImpl @Inject constructor(private val gatewayDataSource: GatewayDataSource) : GatewayRepo {
    override fun initGateway(request: InitGatewayRequest): Flow<BaseCallBack<InitGatewayResponse>> {
        return gatewayDataSource.initGateway(request)
    }

}