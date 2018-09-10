package cz.seznam.kommons.kexts

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import cz.seznam.kommons.app.LifecycleGuardedAction
import cz.seznam.kommons.app.LifecycleGuardedVoidAction

/**
 * @author Jakub Janda
 */


fun LifecycleOwner.guardAction(action: (() -> Unit)): () -> Unit {
	val guardian = LifecycleGuardedVoidAction(lifecycle, action)
	return guardian::invoke
}

fun <P> LifecycleOwner.guardAction(action: ((P) -> Unit)): (param: P) -> Unit {
	val guardian = LifecycleGuardedAction(lifecycle, action)
	return guardian::invoke
}

fun Lifecycle.guardAction(action: (() -> Unit)): () -> Unit {
	val guardian = LifecycleGuardedVoidAction(this, action)
	return guardian::invoke
}

fun <P> Lifecycle.guardAction(action: ((P) -> Unit)): (param: P) -> Unit {
	val guardian = LifecycleGuardedAction(this, action)
	return guardian::invoke
}

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