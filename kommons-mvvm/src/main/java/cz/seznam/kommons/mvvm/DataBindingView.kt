package cz.seznam.kommons.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import kotlin.reflect.KMutableProperty0

/** Implementation of IBindableView using androidx ViewDataBinding.
 *
 * It automatically inflates given layout in createView callback. It also
 * automatically binds viewModel and viewActions to your view, if your layout
 * contains viewModel od viewActions variables.
 *
 * @param viewRes layout res with your view
 *
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
  override fun createView(inflater: LayoutInflater, parent: ViewGroup?, viewState: Bundle?): View {
    val v = DataBindingUtil.inflate<V>(inflater, viewRes, parent, false)
    this.viewBinding = v
    onViewCreated(v, viewState)
    return v.root
  }

  override fun destroyView() {
    super.destroyView()
    viewBinding = null
  }

  /** Callback when the view is created.
   *
   * You can customize, and setup and restore your view here.
   *
   * @param viewBinding inflated view as viewBinding
   * @param viewState previously saved state of the view, null if it is first creation
   */
  open fun onViewCreated(viewBinding: V, viewState: Bundle?) = Unit

  final override fun bind(viewModel: T, viewActions: A?, lifecycleOwner: LifecycleOwner) {
    this.lifecycleOwner = lifecycleOwner
    this.viewActions = viewActions
    viewBinding?.lifecycleOwner = lifecycleOwner
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
    viewBinding?.setVariable(BR.viewModel, null)
    viewBinding?.setVariable(BR.viewActions, null)
    viewBinding?.lifecycleOwner = null
    viewModel = null
    viewActions = null
  }

  /** Called when the view is bound with viewModel and viewActions.
   *
   * In this state is is already bound with ViewDataBinding ,
   * if it contains BR.viewModel or BR.viewActions variables.
   *
   * This is the good place to start observing livedata or observables in the viewmodel.
   *
   * @param viewModel the viewmodel
   * @param viewActions actions the view can invoke
   * @param lifecycleOwner lifecycle owner you can use for observing your livedata
   */
  protected open fun onBind(viewModel: T, viewActions: A?, lifecycleOwner: LifecycleOwner) {}

  /** Called when the view is unbound with the viewmodel.
   *
   * You can manually unbind your livedata or observables, if needed.
   *
   * @param lifecycleOwner lifecycle used to bind the view and viewMode
   *
   */
  protected open fun onUnbind(lifecycleOwner: LifecycleOwner) {}

  /** Operator for nicer notation of livedata observation.
   *
   * Example:
   *
   * ```
   * viewModel.data observeBy this::onDataChanged
   * viewModel.data observeBy {it -> //something useful with it}
   * ```
   *
   * @param setter observer callback on data change
   */
  infix fun <T> LiveData<T>.observeBy(setter: (v: T?) -> Unit) {
    val lifecycleOwner = lifecycleOwner ?: throw RuntimeException("Can't bind unbinded views!")
    this.observe(lifecycleOwner, setter)
  }

  /** Operator for nicer notation of livedata observation.
   *
   * It ignores null values.
   *
   * Example:
   *
   * ```
   * viewModel.data observeNonNullBy this::onDataChanged
   * viewModel.data observeNonNullBy {it -> //something useful with it}
   * ```
   *
   * @param setter observer callback on data change
   */
  infix fun <T> LiveData<T>.observeNonNullBy(setter: (v: T) -> Unit) {
    val lifecycleOwner = lifecycleOwner ?: throw RuntimeException("Can't bind unbinded views!")
    this.observe(lifecycleOwner) {
      if (it != null) {
        setter(it)
      }
    }
  }

  infix fun <T> LiveData<T>.observeBy(property: KMutableProperty0<T?>) {
    val lifecycleOwner = lifecycleOwner ?: throw RuntimeException("Can't bind unbinded views!")
    this.observe(lifecycleOwner) { property.set(it) }
  }
}
