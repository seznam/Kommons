package cz.seznam.kommons.apitools

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build

/**
 * @author Jakub Janda
 */
fun Resources.getColorCompat(colorRes: Int, theme: Resources.Theme? = null): Int {
    return if (Build.VERSION.SDK_INT < 23) {
        getColor(colorRes)
    } else {
        getColor(colorRes, theme)
    }
}