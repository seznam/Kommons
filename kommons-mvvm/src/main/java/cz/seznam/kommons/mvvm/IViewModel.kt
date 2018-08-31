package cz.seznam.kommons.mvvm

import android.os.Bundle

/** Base interface for implementing models by MVVM pattern.
 *
 * Implementations should be used together with KFragment and IBindableView.
 *
 * @author Jakub Janda
 */
interface IViewModel {
	fun saveState(data: Bundle) = Unit

	fun loadState(data: Bundle) = Unit

	fun onBind() {}

	fun onUnbind() {}
}
