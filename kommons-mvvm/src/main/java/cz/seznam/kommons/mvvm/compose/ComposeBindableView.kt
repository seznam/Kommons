package cz.seznam.kommons.mvvm.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import cz.seznam.kommons.mvvm.IBindableView
import cz.seznam.kommons.mvvm.IViewActions
import cz.seznam.kommons.mvvm.IViewModel

abstract class ComposeBindableView<V : IViewModel, A : IViewActions> : IBindableView<V, A> {
  private val bindComponents = MutableLiveData<BindComponents?>()

  override fun createView(
    inflater: LayoutInflater,
    viewLifecycleOwner: LifecycleOwner,
    parent: ViewGroup?,
    viewState: Bundle?
  ): View {
    return ComposeView(inflater.context).apply {
      setContent {
        val bc: BindComponents? by bindComponents.observeAsState()

        val bc2 = bc

        if (bc2 != null) {
          val viewModel = bc2.viewModel
          val viewActions = bc2.viewActions
          createContent(viewModel, viewActions)
        }
      }
    }
  }

  override fun bind(viewModel: V, viewActions: A?, lifecycleOwner: LifecycleOwner) {
    bindComponents.value = BindComponents(viewModel, viewActions)
  }

  override fun unbind(lifecycleOwner: LifecycleOwner) {
    bindComponents.value = null
  }

  @Composable
  abstract fun createContent(viewModel: V, viewActions: A?)

  private inner class BindComponents(val viewModel: V, val viewActions: A?)
}