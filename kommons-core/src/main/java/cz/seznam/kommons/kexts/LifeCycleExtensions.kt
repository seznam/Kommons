package cz.seznam.kommons.kexts

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import cz.seznam.kommons.app.LifecycleGuardedAction
import cz.seznam.kommons.app.LifecycleGuardedVoidAction

/**
 * @author Jakub Janda
 */


/** Guard action with this LifecycleOwner.
 *
 * @param action action to be guarded
 *
 * @return guarded function
 *
 * @see LifecycleGuardedVoidAction
 */
fun LifecycleOwner.guardAction(action: (() -> Unit)): () -> Unit {
	val guardian = LifecycleGuardedVoidAction(lifecycle, action)
	return guardian::invoke
}

/** Guard action with LifecycleOwner.
 *
 * @param action action to be guarded
 *
 * @return guarded function
 *
 * @see LifecycleGuardedAction
 */
fun <P> LifecycleOwner.guardAction(action: ((P) -> Unit)): (param: P) -> Unit {
	val guardian = LifecycleGuardedAction(lifecycle, action)
	return guardian::invoke
}

/** Guard action with this Lifecycle.
 *
 * @param action action to be guarded
 *
 * @return guarded function
 *
 * @see LifecycleGuardedVoidAction
 */
fun Lifecycle.guardAction(action: (() -> Unit)): () -> Unit {
	val guardian = LifecycleGuardedVoidAction(this, action)
	return guardian::invoke
}

/** Guard action with this Lifecycle.
 *
 * @param action action to be guarded
 *
 * @return guarded function
 *
 * @see LifecycleGuardedAction
 */
fun <P> Lifecycle.guardAction(action: ((P) -> Unit)): (param: P) -> Unit {
	val guardian = LifecycleGuardedAction(this, action)
	return guardian::invoke
}

/** Invoke callback, if lifecycle is resumed.
 *
 * @param callback action to invoke when resumed
 */
inline fun LifecycleOwner.ifResumed(callback: () -> Unit) {
	if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
		callback()
	}
}

/** Invoke callback, if lifecycle is resumed.
 *
 * @param callback action to invoke when resumed
 */
inline fun Lifecycle.ifResumed(callback: () -> Unit) {
	if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
		callback()
	}
}