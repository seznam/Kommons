package cz.seznam.kommons.mvvm

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.reflect.KMutableProperty0

/**
 * @author Jakub Janda
 */
open class DataBindingView<T : IViewModel, V : ViewDataBinding, A : IViewActions>(private val viewRes: Int) :
		IBindableView<T, A> {
	var viewBinding: V? = null
	var viewActions: A? = null
	var viewModel: T? = null
	var bound: Boolean = false
	var lifecycleOwner: LifecycleOwner? = null

	@CallSuper
	override fun createView(inflater: LayoutInflater, parent: ViewGroup?): View {
		val v = DataBindingUtil.inflate<V>(inflater, viewRes, parent, false)
		this.viewBinding = v
		return v.root
	}

	/**
	 *
	 */
	final override fun bind(viewModel: T, viewActions: A?, lifecycleOwner: LifecycleOwner) {
		this.lifecycleOwner = lifecycleOwner
		this.viewActions = viewActions
		viewBinding?.setLifecycleOwner(lifecycleOwner)
		viewBinding?.setVariable(BR.viewModel, viewModel)
		viewBinding?.setVariable(BR.viewActions, viewActions)
		this.viewModel = viewModel
		bound = true

		onBind(viewModel, viewActions, lifecycleOwner)
	}

	final override fun unbind(lifecycleOwner: LifecycleOwner) {
		onUnbind(lifecycleOwner)

		this.lifecycleOwner = null
		bound = false
		viewBinding?.unbind()
		viewModel = null
		viewActions = null
	}

	protected open fun onBind(viewModel: T, viewActions: A?, lifecycleOwner: LifecycleOwner) {}

	protected open fun onUnbind(lifecycleOwner: LifecycleOwner) {}

	infix fun <T> LiveData<T>.observeBy(setter: (v: T?) -> Unit) {
		val lifecycleOwner = lifecycleOwner ?: throw RuntimeException("Can't bind unbinded views!")
		this.observe(lifecycleOwner, setter)
	}

	infix fun <T> LiveData<T>.observeBy(property: KMutableProperty0<T?>) {
		val lifecycleOwner = lifecycleOwner ?: throw RuntimeException("Can't bind unbinded views!")
		this.observe(lifecycleOwner) { property.set(it) }
	}
}
