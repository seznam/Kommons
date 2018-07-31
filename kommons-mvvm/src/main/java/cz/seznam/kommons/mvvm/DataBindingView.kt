package cz.seznam.kommons.mvvm

import android.arch.lifecycle.LifecycleOwner
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author Jakub Janda
 */
open class DataBindingView<T : IViewModel, V : ViewDataBinding, A : IViewActions>(private val viewRes: Int) :
		IBindAbleView<T, A> {
	var viewBinding: V? = null
	var viewActions: A? = null
	var viewModel: T? = null
	var bound: Boolean = false

	@CallSuper
	override fun createView(inflater: LayoutInflater, parent: ViewGroup?): View {
		val v = DataBindingUtil.inflate<V>(inflater, viewRes, parent, false)
		this.viewBinding = v
		return v.root
	}

	/**
	 *
	 */
	@CallSuper
	override fun bind(viewModel: T, viewActions: A?, lifecycleOwner: LifecycleOwner) {
		this.viewActions = viewActions
		viewBinding?.setLifecycleOwner(lifecycleOwner)
		viewBinding?.setVariable(BR.viewModel, viewModel)
		viewBinding?.setVariable(BR.viewActions, viewActions)
		this.viewModel = viewModel
		bound = true
	}

	@CallSuper
	override fun unbind(lifecycleOwner: LifecycleOwner) {
		bound = false
		viewBinding?.unbind()
		viewModel = null
	}
}
