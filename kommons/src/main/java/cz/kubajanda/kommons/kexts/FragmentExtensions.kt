package cz.kubajanda.kommons.kexts

import android.os.Bundle
import android.support.v4.app.Fragment
import cz.kubajanda.kommons.app.FragmentGuardedAction
import cz.kubajanda.kommons.app.FragmentGuardedVoidAction

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
	val guardian = FragmentGuardedVoidAction(this, action)
	return guardian::invoke
}

fun <P> Fragment.guardAction(action: ((P) -> Unit)): (param: P) -> Unit {
	val guardian = FragmentGuardedAction(this, action)
	return guardian::invoke
}
