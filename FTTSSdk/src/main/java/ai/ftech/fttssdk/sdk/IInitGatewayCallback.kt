package ai.ftech.fttssdk.sdk

import ai.ftech.fttssdk.domain.exceptions.AppException

interface IInitGatewayCallback {
    fun onSuccess()

    fun onFail(error: AppException?)

}