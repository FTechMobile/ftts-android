package ai.ftech.fttssdk.sdk

import ai.ftech.fttssdk.domain.exceptions.AppException

interface ISTTCallback {
    fun onStart()
    fun onPlaying()
    fun onFail(error: AppException?)
    fun onSuccess(urlAudio: String)
}
