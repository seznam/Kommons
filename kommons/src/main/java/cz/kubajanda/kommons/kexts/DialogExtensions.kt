package cz.kubajanda.kommons.kexts

import android.app.Activity
import android.support.v7.app.AlertDialog

/**
 * @author Jakub Janda
 */

fun AlertDialog.Builder.showSafe(activity: Activity) {
	if (!activity.isFinishing) {
		this.show()
	}
}
