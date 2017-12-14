package cz.seznam.kommons.kexts

import android.os.Bundle
import android.support.v4.app.Fragment
import cz.seznam.kommons.app.LifecycleGuardedAction
import cz.seznam.kommons.app.LifecycleGuardedVoidAction

/**
 * @author Jakub Janda
 */

fun <T : Fragment> T.withArgs(applyArgs: Bundle.() -> Unit): T {
	val args = Bundle()
	applyArgs(args)
	this.arguments = args

	return this
}


fun Fragment.guardAction(action: (() -> Unit)): () -> Unit {
	val guardian = LifecycleGuardedVoidAction(lifecycle, action)
	return guardian::invoke
}

fun <P> Fragment.guardAction(action: ((P) -> Unit)): (param: P) -> Unit {
	val guardian = LifecycleGuardedAction(lifecycle, action)
	return guardian::invoke
}
