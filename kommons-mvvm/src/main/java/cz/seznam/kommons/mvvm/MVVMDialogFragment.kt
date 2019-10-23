package cz.seznam.kommons.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

abstract class MVVMDialogFragment<M : IViewModel, A : IViewActions> : DialogFragment() {
  abstract val viewModel: M?

  abstract val view: IBindableView<M, A>?

  abstract val viewActions: A?

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = view
    val viewModel = viewModel

    if (view != null && viewModel != null) {
      val v = view.createView(obtainInflater(inflater), container)

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

  open fun obtainInflater(origInflater: LayoutInflater): LayoutInflater = origInflater
}