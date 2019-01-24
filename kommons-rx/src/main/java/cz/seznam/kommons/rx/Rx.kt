package cz.seznam.kommons.rx

import android.util.Log

/**
 * @author Jakub Janda
 */
object Rx {
    var schedulers: IRxSchedulers = StandardRxSchedulers()

    var defaultErrorHandler: ((error: Throwable) -> Unit) = { Log.e("DefaultRxHandler", it.toString()) }
}
