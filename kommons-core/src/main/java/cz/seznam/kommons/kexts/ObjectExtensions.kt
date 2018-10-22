package cz.seznam.kommons.kexts

import android.util.Log


/** Log.v with object class name as tag.
 *
 * @param message message to log
 *
 * @author Jakub Janda
 */
fun Any.logV(message: String) {
	Log.v(this.javaClass.simpleName, message)
}

/** Log.d with object class name as tag.
 *
 * @param message message to log
 *
 * @author Jakub Janda
 */
fun Any.logD(message: String) {
	Log.d(this.javaClass.simpleName, message)
}

/** Log.w with object class name as tag.
 *
 * @param message message to log
 *
 * @author Jakub Janda
 */
fun Any.logW(message: String) {
	Log.w(this.javaClass.simpleName, message)
}

/** Log.e with object class name as tag.
 *
 * @param message message to log
 *
 * @author Jakub Janda
 */
fun Any.logE(message: String) {
	Log.e(this.javaClass.simpleName, message)
}
