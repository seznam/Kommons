package cz.seznam.kommons.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner

/** Base interface for implementing view by MVVM pattern.
 *
 * @author Jakub Janda
 */
interface IBindableView<in T : IViewModel, in A : IViewActions> {

  /** Creates view.
   *
   * Prepare your view. It's called before bind().
   *
   * @param inflater inflater you can use to create your view
   * @param parent parent viewgroup for your view
   *
   * @return your view
   */
  fun createView(inflater: LayoutInflater, parent: ViewGroup?, viewState: Bundle? = null): View

  /** Called when view is destroyed.
   *
   * Your chance to clean up view stuff. It's called after unbind.
   */
  fun destroyView() {

  }

  /** Saves the view state for later.
   *
   * @param state bundle when you can save your state
   */
  fun saveViewState(state: Bundle) {}

  /** Binds model to your view.
   *
   * It's called when view and model is ready for use together, typically in some onViewCreated phase,
   * after createView() is called.
   *
   * @param viewModel model for your view
   * @param viewActions actions your view can invoke
   * @param lifecycleOwner lifecycle owner you can use for observing livedata from your model
   */
  fun bind(
    viewModel: T,
    viewActions: A?,
    lifecycleOwner: LifecycleOwner
  )

  /** Unbinds model from your view.
   *
   * Here you can unbind your model from view, for example when using DataBinding.
   * Typically called in onDestroyView phase.
   *
   * @param lifecycleOwner lifecycle owner used to bind your view and viewmodel
   */
  fun unbind(lifecycleOwner: LifecycleOwner)
}
