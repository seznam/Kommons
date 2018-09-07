package cz.seznam.kommons.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author Jakub Janda
 */
class StandardRxSchedulers : IRxSchedulers {
	override fun io(): Scheduler = Schedulers.io()

	override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()

	override fun newThread(): Scheduler = Schedulers.newThread()

	override fun computation(): Scheduler = Schedulers.computation()
}
