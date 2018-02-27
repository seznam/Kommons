package cz.seznam.kommons.mvvm.kexts

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData

/**
 * @author Jakub Janda
 */

fun <T : Any?> LiveData<T>.observe(owner: LifecycleOwner,
																	 callback: (value: T?) -> Unit) = observe(owner, android.arch.lifecycle.Observer {
	callback.invoke(it)
})
