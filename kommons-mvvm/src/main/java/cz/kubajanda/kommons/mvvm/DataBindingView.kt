package cz.kubajanda.kommons.mvvm

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
open class DataBindingView<in T : IViewModel, V : ViewDataBinding>(private val viewRes: Int) : IBindableView<T> {
	var viewBinding: V? = null

	@CallSuper
	override fun createView(inflater: LayoutInflater, parent: ViewGroup): View {
		val v = DataBindingUtil.inflate<V>(inflater, viewRes, parent, false)
		this.viewBinding = v
		return v.root
	}

	/**
	 *
	 */
	@CallSuper
	override fun bind(viewModel: T, lifecycleOwner: LifecycleOwner) {
		viewBinding?.setVariable(BR.viewModel, viewModel)
	}

	@CallSuper
	override fun unbind() {
		viewBinding?.unbind()
		viewBinding = null
	}
}
