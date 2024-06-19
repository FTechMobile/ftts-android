package ai.ftech.fttssdk.sdk

import ai.ftech.fttssdk.domain.exceptions.AppException
import ai.ftech.fttssdk.domain.model.LanguageConfigData

interface ILanguageConfigCallback {
    fun onSuccess(listLanguage:List<LanguageConfigData>)
    fun onFail(error: AppException?)
}