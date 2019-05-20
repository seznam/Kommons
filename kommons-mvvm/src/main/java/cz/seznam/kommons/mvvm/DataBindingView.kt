package cz.seznam.kommons.mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import kotlin.reflect.KMutableProperty0

/**
 * @author Jakub Janda
 */
open class DataBindingView<T : IViewModel, V : ViewDataBinding, A : IViewActions>(private val viewRes: Int) :
    IBindableView<T, A> {
    var viewBinding: V? = null
    var viewActions: A? = null
    var viewModel: T? = null
    override var isBound: Boolean = false
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
        viewBinding?.lifecycleOwner = lifecycleOwner
        viewBinding?.setVariable(BR.viewModel, viewModel)
        viewBinding?.setVariable(BR.viewActions, viewActions)
        this.viewModel = viewModel
        isBound = true

        onBind(viewModel, viewActions, lifecycleOwner)
    }

    final override fun unbind(lifecycleOwner: LifecycleOwner) {
        onUnbind(lifecycleOwner)

        this.lifecycleOwner = null
        isBound = false
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
