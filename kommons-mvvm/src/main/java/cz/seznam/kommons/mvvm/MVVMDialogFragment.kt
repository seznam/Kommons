package cz.seznam.kommons.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner

/** Base dialog fragment for connecting view, viewmodel and viewactions together.
 *
 * Implementations have to provide view and viewmodel. Fragment itself is responsible
 * for linking these two components in the right time. It can also provide viewActions
 * with actions invokable by views.
 *
 * The view is created in onCreateView and it's bound with viewModel and viewActions.
 *
 * The view is unbound and destroyed in onDestroyView callback in this order.
 *
 * The view state is saved in onSaveInstanceState callback.
 *
 * @author Jakub Janda
 */
abstract class MVVMDialogFragment<M : IViewModel, A : IViewActions> : DialogFragment() {
  /** ViewModel for the view.
   */
  abstract val viewModel: M?

  /** View bindable with the view.
   */
  abstract val view: IBindableView<M, A>?

  /** View actions invokable by the view.
   */
  abstract val viewActions: A?

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = view
    val viewModel = viewModel

    if (view != null && viewModel != null) {
      val v = view.createView(obtainInflater(inflater), viewLifecycleOwner, container)

      view.bind(viewModel, viewActions, this)
      viewModel.onBind()
      return v
    } else {
      return super.onCreateView(inflater, container, savedInstanceState)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()

    view?.unbind(this)
    view?.destroyView()
    viewModel?.onUnbind()
  }

  /** Obtains inflater for creating the view.
   *
   * Default implementation returns the one given in onCreateView callback.
   * You can provide your own implementation or customization, for example when
   * using different theme than default.
   *
   * It's useful when recreating views during fragment life.
   *
   * @param origInflater original inflater given in onCreateView callback
   *
   * @return inflater used for creating the view
   */
  open fun obtainInflater(origInflater: LayoutInflater): LayoutInflater = origInflater
}