package cz.seznam.kommons.apitools

import android.content.pm.PackageInfo
import android.os.Build

/**
 * @author Jakub Janda
 */

val PackageInfo.version: Long
    get() {
        return if (Build.VERSION.SDK_INT >= 28) {
            longVersionCode
        } else {
            versionCode.toLong()
        }
    }