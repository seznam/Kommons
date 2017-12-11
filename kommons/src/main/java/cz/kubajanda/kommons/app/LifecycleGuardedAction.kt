package cz.kubajanda.kommons.app

import android.arch.lifecycle.Lifecycle
import java.lang.ref.WeakReference

/**
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
