package cz.seznam.kommons.mvvm

import androidx.lifecycle.LifecycleOwner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author Jakub Janda
 */
data class MVVMContainer<M : IViewModel, A : IViewActions>(val view: IBindableView<M, A>,
																													 val viewModel: M,
																													 val viewActions: A?) {
	fun createView(inflater: LayoutInflater, parent: ViewGroup?): View = view.createView(inflater, parent)

	fun bind(lifecycleOwner: LifecycleOwner) {
		view.bind(viewModel, viewActions, lifecycleOwner)
		viewModel.onBind()
	}

	fun unbind(lifecycleOwner: LifecycleOwner) {
		view.unbind(lifecycleOwner)
		viewModel.onUnbind()
	}
}