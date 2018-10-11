package cz.seznam.kommons.kexts

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.DimenRes
import android.widget.Toast

/**
 * Created by kubaspatny on 26/02/2018.
 */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
	Toast.makeText(this, message, duration).show()
}

fun Context.showToast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
	Toast.makeText(this, message, duration).show()
}

fun Context.pixelSizeOf(@DimenRes dimenRes: Int): Int = resources.getDimensionPixelSize(dimenRes)


fun Context.startServiceInForeground(intent: Intent) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		startForegroundService(intent)
	} else {
		startService(intent)
	}
}