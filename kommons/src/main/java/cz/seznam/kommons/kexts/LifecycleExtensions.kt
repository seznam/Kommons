package cz.seznam.kommons.kexts

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner

/**
 * @author Jakub Janda
 */
inline fun LifecycleOwner.ifResumed(callback: () -> Unit) {
	if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
		callback()
	}
}

inline fun Lifecycle.ifResumed(callback: () -> Unit) {
	if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
		callback()
	}
}