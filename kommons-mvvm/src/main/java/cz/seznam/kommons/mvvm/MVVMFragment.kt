package cz.seznam.kommons.mvvm

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/** Base fragment for implementing Views by Model View ViewModel pattern.
 *
 * Implementations have to provide view and model. Fragment itself is responsible for linking these two components
 * in the right time.
 *
 * @author Jakub Janda
 */
abstract class MVVMFragment<M : IViewModel, A : IViewActions> : Fragment() {
	abstract val viewModel: M?

	abstract val view: IBindAbleView<M, A>?

	abstract val viewActions: A?

	override fun onCreateView(inflater: LayoutInflater,
														container: ViewGroup?,
														savedInstanceState: Bundle?): View? {
		val view = view
		val viewModel = viewModel

		if (view != null && viewModel != null) {
			val v = view.createView(inflater, container)

			if (savedInstanceState != null) {
				viewModel.loadState(savedInstanceState)
			}

			view.bind(viewModel, viewActions, this)
			viewModel.onBind()
			return v
		} else {
			return super.onCreateView(inflater, container, savedInstanceState)
		}
	}


	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		viewModel?.saveState(outState)
	}

	override fun onDestroyView() {
		super.onDestroyView()

		view?.unbind(this)
		viewModel?.onUnbind()
	}
}
