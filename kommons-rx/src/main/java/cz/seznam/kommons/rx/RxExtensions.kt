package cz.seznam.kommons.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * @author Jakub Janda
 */


fun <T : Any> Observable<T>.subsOnIO(): Observable<T> = this.subscribeOn(Rx.schedulers.io())

fun <T : Any> Flowable<T>.subsOnIO(): Flowable<T> = this.subscribeOn(Rx.schedulers.io())

fun <T : Any> Single<T>.subsOnIO(): Single<T> = this.subscribeOn(Rx.schedulers.io())

fun <T : Any> Completable.subsOnIO(): Completable = this.subscribeOn(Rx.schedulers.io())

fun <T : Any> Observable<T>.subsOnUi(): Observable<T> = this.subscribeOn(Rx.schedulers.mainThread())

fun <T : Any> Flowable<T>.subsOnUi(): Flowable<T> = this.subscribeOn(Rx.schedulers.mainThread())

fun <T : Any> Single<T>.subsOnUi(): Single<T> = this.subscribeOn(Rx.schedulers.mainThread())

fun <T : Any> Completable.subsOnUi(): Completable = this.subscribeOn(Rx.schedulers.mainThread())

fun <T : Any> Observable<T>.subsOnNewThread(): Observable<T> = this.subscribeOn(Rx.schedulers.newThread())

fun <T : Any> Flowable<T>.subsOnNewThread(): Flowable<T> = this.subscribeOn(Rx.schedulers.newThread())

fun <T : Any> Single<T>.subsOnNewThread(): Single<T> = this.subscribeOn(Rx.schedulers.newThread())

fun Completable.subsOnNewThread(): Completable = this.subscribeOn(Rx.schedulers.newThread())

fun <T : Any> Observable<T>.subsOnComputation(): Observable<T> = this.subscribeOn(Rx.schedulers.computation())

fun <T : Any> Flowable<T>.subsOnComputation(): Flowable<T> = this.subscribeOn(Rx.schedulers.computation())

fun <T : Any> Single<T>.subsOnComputation(): Single<T> = this.subscribeOn(Rx.schedulers.computation())

fun Completable.subsOnComputation(): Completable = this.subscribeOn(Rx.schedulers.computation())

fun <T : Any> Observable<T>.obsOnUI(): Observable<T> = this.observeOn(Rx.schedulers.mainThread())

fun <T : Any> Flowable<T>.obsOnUI(): Flowable<T> = this.observeOn(Rx.schedulers.mainThread())

fun <T : Any> Single<T>.obsOnUI(): Single<T> = this.observeOn(Rx.schedulers.mainThread())

fun Completable.obsOnUI(): Completable = this.observeOn(Rx.schedulers.mainThread())


fun <T : Any> Observable<T>.safeSubscribe(onSuccess: (T) -> Unit): Disposable {
	return safeSubscribe(onSuccess, Rx.defaultErrorHandler, null)
}

/** Subscribes observable and guard callbacks from call after unsubscribe is called.
 *
 * @return disposable
 */
fun <T : Any> Observable<T>.safeSubscribe(onSuccess: (T) -> Unit,
																					onError: (Throwable) -> Unit,
																					onComplete: (() -> Unit)? = null): Disposable {
	val subscriptionLock = Object()
	var subscriptionActive = true

	return this.doFinally { synchronized(subscriptionLock) { subscriptionActive = false } }.subscribe(
			{ synchronized(subscriptionLock) { if (subscriptionActive) (onSuccess(it)) } },
			{ synchronized(subscriptionLock) { if (subscriptionActive) (onError(it)) } },
			{
				if (onComplete != null) {
					synchronized(subscriptionLock) { if (subscriptionActive) (onComplete()) }
				}
			})
}

fun <T : Any> Single<T>.safeSubscribe(onSuccess: (T) -> Unit): Disposable {
	return safeSubscribe(onSuccess, onError = Rx.defaultErrorHandler)
}

/** Subscribes observable and guard callbacks from call after unsubscribe is called.
 *
 * @return disposable
 */
fun <T : Any> Single<T>.safeSubscribe(onSuccess: (T) -> Unit,
																			onError: (Throwable) -> Unit): Disposable {
	val subscriptionLock = Object()
	var subscriptionActive = true

	return this.doFinally { synchronized(subscriptionLock) { subscriptionActive = false } }.subscribe(
			{ synchronized(subscriptionLock) { if (subscriptionActive) (onSuccess(it)) } },
			{ synchronized(subscriptionLock) { if (subscriptionActive) (onError(it)) } })
}

inline fun Completable.safeSubscribe(crossinline onComplete: () -> Unit = {}): Disposable {
	return safeSubscribe(onComplete, onError = Rx.defaultErrorHandler)
}

inline fun Completable.safeSubscribe(crossinline onComplete: () -> Unit = {},
																		 crossinline onError: (Throwable) -> Unit): Disposable {
	val subscriptionLock = Object()
	var subscriptionActive = true

	return this.doFinally { synchronized(subscriptionLock) { subscriptionActive = false } }.subscribe(
			{ synchronized(subscriptionLock) { if (subscriptionActive) (onComplete()) } },
			{ synchronized(subscriptionLock) { if (subscriptionActive) (onError(it)) } })
}


/** Subscribes observable and guard callbacks from call after unsubscribe is called.
 *
 * @return disposable
 */
fun <T : Any> Flowable<T>.safeSubscribe(onNext: (T) -> Unit,
																				onError: (Throwable) -> Unit = Rx.defaultErrorHandler,
																				onComplete: (() -> Unit)? = null): Disposable {
	val subscriptionLock = Object()
	var subscriptionActive = true

	return this.doFinally { synchronized(subscriptionLock) { subscriptionActive = false } }.subscribe(
			{ synchronized(subscriptionLock) { if (subscriptionActive) (onNext(it)) } },
			{ synchronized(subscriptionLock) { if (subscriptionActive) (onError(it)) } },
			{
				if (onComplete != null) {
					synchronized(subscriptionLock) { if (subscriptionActive) (onComplete()) }
				}
			})
}

/** Creates Observable.interval and subscribe it with safeSubscribe.
 *
 *	It is subscribed and observed on computation sched.
 *
 * @param interval interval after which is callback called
 * @param timeUnit unit of interval, default is milliseconds
 * @param callback callback which is called in interval
 */
inline fun startTimer(interval: Long,
											timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
											crossinline callback: () -> Unit): Disposable = Flowable.interval(interval, timeUnit)
		.onBackpressureLatest()
		.subsOnComputation()
		.safeSubscribe({ callback() }, {}, {})

/** Creates Observable.interval and subscribe it with safeSubscribe on main thread.
 *
 *
 * @param interval interval after which is callback called
 * @param timeUnit unit of interval, default is milliseconds
 * @param callback callback which is called in interval
 */
inline fun startUiTimer(interval: Long,
												timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
												crossinline callback: () -> Unit): Disposable = Flowable.interval(interval, timeUnit)
		.observeOn(AndroidSchedulers.mainThread())
		.onBackpressureLatest()
		.safeSubscribe({ callback() }, {}, {})

/** Creates Observable.timer and subscribes it with safeSubscribe on main thread.
 *
 * @param interval interval before callback is called
 * @param timeUnit unit of interval, default is milliseconds
 * @param callback callback which is called after interval
 */
inline fun startUiCountdown(interval: Long,
														timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
														crossinline callback: () -> Unit): Disposable {
	return Flowable.timer(interval, timeUnit)
			.onBackpressureLatest()
			.observeOn(AndroidSchedulers.mainThread())
			.safeSubscribe({ callback() }, {}, {})
}

val Disposable.isNotDisposed: Boolean
	get() = !this.isDisposed

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
	add(disposable)
}