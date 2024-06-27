package ai.ftech.fttssdk.sdk

import ai.ftech.fttssdk.domain.exceptions.AppException

interface ITTSCallback {
    fun onStart()
    fun onPlaying()
    fun onFail(error: AppException?)
    fun onSuccess(urlAudio: String)
    fun onFileSuccess(listUrlAudio: List<String>)
}
