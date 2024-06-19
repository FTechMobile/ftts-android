package ai.ftech.fttssdk.domain.exceptions

import ai.ftech.fttssdk.utils.AppConstant

data class AppException(
    override var message: String? = null,
    var statusCode: Int = AppConstant.UNKNOWN_ERROR,
): Exception()