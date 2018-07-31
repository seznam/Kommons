package cz.seznam.kommons.kexts

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations

/**
 * @author Jakub Janda
 */

fun <T : Any?> LiveData<T>.observe(owner: LifecycleOwner,
																	 callback: (value: T?) -> Unit) = observe(owner, android.arch.lifecycle.Observer {
	callback.invoke(it)
})

fun <X, Y> LiveData<X>.map(mapper: (X?) -> Y): LiveData<Y> = Transformations.map(this, mapper)

/** Nastavi danou hodnotu a nasledne prenastavi data zpet na null.
 *
 * Vhodne pro data typu Event, kdy potrebujeme propagovat hodnotu jen aktivnim observerum
 * a nepotrebujeme si pamatovat hodnotu.
 */
fun <T> MutableLiveData<T>.shoot(value: T) {
	setValue(value)
	setValue(null)
}