package cz.seznam.kommons.apitools

import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * @author Jakub Janda
 */


object HtmlApi {
    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT < 24) {
            Html.fromHtml(source)
        } else {
            Html.fromHtml(source, 0)
        }
    }
}