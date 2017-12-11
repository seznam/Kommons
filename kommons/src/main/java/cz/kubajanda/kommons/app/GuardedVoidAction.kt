package cz.kubajanda.kommons.app

/**
 * @author Jakub Janda
 */
abstract class GuardedVoidAction(private val action: () -> Unit) {
	var invokeMinInterval = DEFAULT_MIN_INTERVAL
	private var lastInvokeTime = 0L

	fun invoke() {
		val currentTime = System.currentTimeMillis()
		val timeDiff = currentTime - lastInvokeTime

		if (timeDiff > invokeMinInterval && isInvokable()) {
			lastInvokeTime = currentTime
			action()
		}
	}

	abstract fun isInvokable(): Boolean

	companion object {
		const val DEFAULT_MIN_INTERVAL = 500L
	}
}
