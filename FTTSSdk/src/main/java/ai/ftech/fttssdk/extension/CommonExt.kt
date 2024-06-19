package ai.ftech.fttssdk.extension

import ai.ftech.fttssdk.sdk.TTSManager
import android.content.Context
import androidx.annotation.StringRes
import java.io.File

fun String?.getString(defaultValue: String = ""): String {
    return if(this.isNullOrEmpty()) defaultValue else this
}

fun getAppString(
    @StringRes stringId: Int,
    context: Context? = TTSManager.getApplicationContext()
): String {
    return context?.getString(stringId) ?: ""
}

fun File.copyTo(target: File): Boolean {
    inputStream().use { input ->
        target.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return true
}