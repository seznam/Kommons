package cz.seznam.kommons.kexts

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
