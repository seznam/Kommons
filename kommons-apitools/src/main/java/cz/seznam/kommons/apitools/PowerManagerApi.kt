package cz.seznam.kommons.apitools

import android.os.Build
import android.os.PowerManager

/**
 * @author Jakub Janda
 */
val PowerManager.isInteractiveComp: Boolean
    get() {
        return if (Build.VERSION.SDK_INT < 20) {
            isScreenOn
        } else {
            isInteractive
        }
    }