package cz.seznam.kommons.kexts

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author Jakub Janda
 */


fun <T : Any> Observable<T>.subscribeIO(): Observable<T> = this.subscribeOn(Schedulers.io())

fun <T : Any> Observable<T>.observeMain(): Observable<T> = this.observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Single<T>.subscribeIO(): Single<T> = this.subscribeOn(Schedulers.io())

fun <T : Any> Single<T>.subscribeComp(): Single<T> = this.subscribeOn(Schedulers.computation())

fun <T : Any> Single<T>.onNewThread(): Single<T> = this.subscribeOn(Schedulers.newThread())

fun <T : Any> Single<T>.observeMain(): Single<T> = this.observeOn(AndroidSchedulers.mainThread())

fun Completable.subscribeIO(): Completable = this.subscribeOn(Schedulers.io())

fun Completable.subscribeComp(): Completable = this.subscribeOn(Schedulers.computation())

fun Completable.onNewThread(): Completable = this.subscribeOn(Schedulers.newThread())

fun Completable.observeMain(): Completable = this.observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Observable<T>.safeSubscribe(onSuccess: (T) -> Unit): Disposable {
	return safeSubscribe(onSuccess, {
		logW(it.toString())
	}, null)
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
	return safeSubscribe(onSuccess, onError = {
		logW(it.toString())
	})
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
	return safeSubscribe(onComplete, onError = {
		logW(it.toString())
	})
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
																				onError: (Throwable) -> Unit = {
																					logW(it.toString())
																				},
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

val Disposable.isSubscribed: Boolean
	get() = !this.isDisposed

val Disposable.isNotDisposed: Boolean
	get() = !this.isDisposed

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
	add(disposable)
}