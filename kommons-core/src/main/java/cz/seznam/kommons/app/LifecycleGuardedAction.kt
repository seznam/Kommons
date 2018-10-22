package cz.seznam.kommons.app

import android.arch.lifecycle.Lifecycle
import java.lang.ref.WeakReference

/** Guarded action which depends on lifecycle.
 *
 * Action is invokable, if current lifecycle state is at least resumed.
 *
 * @author Jakub Janda
 */
class LifecycleGuardedAction<P>(lifecycle: Lifecycle,
																action: (P) -> Unit) : GuardedAction<P>(action) {
	private val lifecycleRef = WeakReference(lifecycle)

	override fun isInvokable(): Boolean {
		val lifecycle = lifecycleRef.get()

		return lifecycle != null && lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
	}
}
