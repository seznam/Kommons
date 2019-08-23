package cz.seznam.kommons.kexts

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes

/** Shows Toast message.
 *
 * @param message toast message
 * @param duration toast duration, can be Toast.LENGTH_SHORT or Toast.LENGTH_LONG
 *
 * @author Jakub Spatny
 */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
  Toast.makeText(this, message, duration).show()
}

/** Shows Toast message.
 *
 * @param message toast message res id
 * @param duration toast duration, can be Toast.LENGTH_SHORT or Toast.LENGTH_LONG
 *
 * @author Jakub Spatny
 */
fun Context.showToast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
  Toast.makeText(this, message, duration).show()
}

/** Returns pixel size of given dime res.
 *
 * @param dimenRes dimension resource
 *
 * @return dimenion in pixels
 */
fun Context.px(@DimenRes dimenRes: Int): Int = resources.getDimensionPixelSize(dimenRes)

/** Start service in foreground.
 *
 * If API>=26 it calls startForegroundService, otherwise startService.
 *
 * @param intent intent to start service
 */
fun Context.startServiceInForeground(intent: Intent) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    startForegroundService(intent)
  } else {
    startService(intent)
  }
}

fun Context.getThemeColor(@AttrRes attr: Int, resolveValue: Boolean = true): Int {
  val typedValue = TypedValue()
  theme.resolveAttribute(attr, typedValue, resolveValue)
  return typedValue.data
}
