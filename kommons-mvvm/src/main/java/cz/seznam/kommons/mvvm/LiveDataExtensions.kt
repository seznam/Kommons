package cz.seznam.kommons.mvvm

import android.arch.lifecycle.*
import kotlin.reflect.KMutableProperty0

/**
 * @author Jakub Janda
 */

fun <T : Any?> LiveData<T>.observe(owner: LifecycleOwner,
																	 callback: (value: T?) -> Unit) = observe(owner, android.arch.lifecycle.Observer {
	callback.invoke(it)
})


class LifecycleBindings(val lifecycleOwner: LifecycleOwner) {
	fun <T> LiveData<T>.observe(callback: (value: T?) -> Unit) = observe(lifecycleOwner, Observer {
		callback.invoke(it)
	})

	infix fun <T> LiveData<T>.with(setter: (v: T?) -> Unit) {
		this.observe(setter)
	}

	infix fun <T> LiveData<T>.with(property: KMutableProperty0<T?>) {
		this.observe { property.set(it) }
	}

	infix fun <T> KMutableProperty0<T?>.with(v: LiveData<T>) {
		v.observe(::set)
	}
}

fun LifecycleOwner.bind(init: LifecycleBindings.() -> Unit): LifecycleBindings {
	val views = LifecycleBindings(this)  // create the receiver object
	views.init()        // pass the receiver object to the lambda
	return views
}

fun <X, Y> LiveData<X>.map(mapper: (X) -> Y): LiveData<Y> = Transformations.map(this, mapper)

/** Nastavi danou hodnotu a nasledne prenastavi data zpet na null.
 *
 * Vhodne pro data typu Event, kdy potrebujeme propagovat hodnotu jen aktivnim observerum
 * a nepotrebujeme si pamatovat hodnotu.
 */
fun <T> MutableLiveData<T>.shoot(value: T) {
	setValue(value)
	setValue(null)
}

