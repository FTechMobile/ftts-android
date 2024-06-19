package ai.ftech.fttssdk.data.source

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ai.ftech.fttssdk.domain.exceptions.AppException
import ai.ftech.fttssdk.domain.model.BaseCallBack
import ai.ftech.fttssdk.domain.model.BaseResponse
import ai.ftech.fttssdk.domain.model.ErrorResponse
import ai.ftech.fttssdk.utils.AppConstant
import retrofit2.HttpException
import java.net.SocketTimeoutException

fun <RESPONSE : BaseResponse> Result<RESPONSE>.handle(): BaseCallBack<RESPONSE> {
    val response = this.getOrElse { exception: Throwable ->
        return when (exception) {
            is HttpException -> {
                var message = exception.message
                val errorBody = exception.response()?.errorBody()?.string()
                if (!errorBody.isNullOrEmpty()) {
                    try {
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        message = errorResponse.message
                    } catch (e: JsonSyntaxException) {
                        message = errorBody
                    }

                }
                BaseCallBack.Error(error = AppException(message = message,
                    statusCode = exception.code()))
            }

            is SocketTimeoutException -> {
                BaseCallBack.Error(error = AppException(message = exception.message,
                    statusCode = AppConstant.TIME_OUT_ERROR))
            }

            else -> BaseCallBack.Error(error = AppException(message = exception.message,
                statusCode = AppConstant.UNKNOWN_ERROR))
        }
    }

    return if (response.isSuccessful()) {
        BaseCallBack.Success(data = response)
    } else {
        BaseCallBack.Error(error = AppException(message = response.message, statusCode = response.code
            ?: AppConstant.UNKNOWN_ERROR))
    }
}

