package cz.seznam.kommons.app

/** Base class for writing guarders of actions without parameter.
 *
 * Guarded action is invoked when isInvokable returns true and interval of last execution is passed.
 * It can be useful when invoking actions from UI, for example button clicks, and we don't want the action
 * repeat in some interval. For example during animation, when there is possibility to click the same element twice
 * and open screen twice, etc.
 *
 * Default interval is 500 ms.
 *
 * @author Jakub Janda
 */
abstract class GuardedVoidAction(private val action: () -> Unit) {
	/** Interval after which the action can be invoked again. In millis.
	 */
	var invokeMinInterval = DEFAULT_MIN_INTERVAL

	private var lastInvokeTime = 0L

	/** Invokes the action, if possible.
	 */
	fun invoke() {
		val currentTime = System.currentTimeMillis()
		val timeDiff = currentTime - lastInvokeTime

		if (timeDiff > invokeMinInterval && isInvokable()) {
			lastInvokeTime = currentTime
			action()
		}
	}

	/** Return if the action is invokable at this time.
	 *
	 * @return true, if action is invokable at this time, otherwise false
	 */
	abstract fun isInvokable(): Boolean

	companion object {
		const val DEFAULT_MIN_INTERVAL = 500L
	}
}
