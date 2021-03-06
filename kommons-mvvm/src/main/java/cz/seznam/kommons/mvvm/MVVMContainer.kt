package cz.seznam.kommons.mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner

/**
 * @author Jakub Janda
 */
data class MVVMContainer<M : IViewModel, A : IViewActions>(
  val view: IBindableView<M, A>,
  val viewModel: M,
  val viewActions: A?
) {
  fun createView(
    inflater: LayoutInflater,
    viewLifecycleOwner: LifecycleOwner,
    parent: ViewGroup?
  ): View = view.createView(inflater, viewLifecycleOwner, parent)

  fun destroyView() = view.destroyView()

  fun bind(lifecycleOwner: LifecycleOwner) {
    view.bind(viewModel, viewActions, lifecycleOwner)
    viewModel.onBind()
  }

  fun unbind(lifecycleOwner: LifecycleOwner) {
    view.unbind(lifecycleOwner)
    viewModel.onUnbind()
  }
}