package cz.seznam.kommons.kexts

import android.support.v7.app.AppCompatActivity
import cz.seznam.kommons.app.LifecycleGuardedAction
import cz.seznam.kommons.app.LifecycleGuardedVoidAction

/**
 * @author Jakub Janda
 */


fun AppCompatActivity.guardAction(action: (() -> Unit)): () -> Unit {
	val guardian = LifecycleGuardedVoidAction(lifecycle, action)
	return guardian::invoke
}

fun <P> AppCompatActivity.guardAction(action: ((P) -> Unit)): (param: P) -> Unit {
	val guardian = LifecycleGuardedAction(lifecycle, action)
	return guardian::invoke
}
