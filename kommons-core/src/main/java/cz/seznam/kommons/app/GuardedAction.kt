package cz.seznam.kommons.app

/**
 * @author Jakub Janda
 */
abstract class GuardedAction<P>(private val action: (P) -> Unit) {
	var invokeMinInterval = DEFAULT_MIN_INTERVAL
	var lastInvokeTime = 0L

	fun invoke(param: P) {
		val currentTime = System.currentTimeMillis()
		val timeDiff = currentTime - lastInvokeTime

		if (timeDiff > invokeMinInterval && isInvokable()) {
			lastInvokeTime = currentTime
			return action(param)
		}
	}

	abstract fun isInvokable(): Boolean

	companion object {
		const val DEFAULT_MIN_INTERVAL = 500L
	}
}
