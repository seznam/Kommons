package cz.seznam.kommons.mvvm

import android.arch.lifecycle.LifecycleOwner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/** Base interface for implementig views by MVVM pattern.
 *
 * Implementations should be used together with KFragment and IViewModel.
 *
 * @author Jakub Janda
 */
interface IBindableView<in T : IViewModel> {

	/** Creates view.
	 *
	 * Prepare your view . It's called before bind().
	 *
	 * @param inflater inflater you can use to create your view
	 * @param parent parent viewgroup for your view
	 *
	 * @return your view
	 */
	fun createView(inflater: LayoutInflater,
								 parent: ViewGroup): View

	/** Binds model to your view.
	 *
	 * It's called whne view and model is ready for use together, typically in some onViewCreated phase,
	 * after createView() is called.
	 *
	 * @param viewModel model for your view
	 * @param lifecycleOwner lifecycle owner you can use for observing livedata from your model
	 */
	fun bind(viewModel: T,
					 lifecycleOwner: LifecycleOwner)

	/** Unbinds model from your view.
	 *
	 * Here you can unbind your model from view, for example when using DataBinding.
	 * Typically called in onDestroyView phase.
	 *
	 */
	fun unbind()
}
