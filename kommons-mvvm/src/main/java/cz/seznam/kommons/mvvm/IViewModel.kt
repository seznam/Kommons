package cz.seznam.kommons.mvvm

/** Base interface for implementing models by MVVM pattern.
 *
 * Implementations should be used together with KFragment and IBindableView.
 *
 * @author Jakub Janda
 */
interface IViewModel {
	fun onBind() {}

	fun onUnbind() {}
}
