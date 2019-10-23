package cz.seznam.kommons.mvvm

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

/**
 * @author Jakub Janda
 */

inline fun <reified V : ViewModel> Fragment.obtainViewModel(crossinline viewModelFactory: () -> V): V {
  return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
    override fun <V : ViewModel?> create(modelClass: Class<V>): V {
      return viewModelFactory() as V
    }
  }).get(V::class.java)
}

inline fun <reified V : ViewModel> Fragment.obtainViewModelWithState(crossinline viewModelFactory: (savedStateHandle: SavedStateHandle) -> V): V {
  return ViewModelProviders.of(this, object : AbstractSavedStateViewModelFactory(this, null) {
    override fun <V : ViewModel?> create(
      key: String,
      modelClass: Class<V>,
      handle: SavedStateHandle
    ): V {
      return viewModelFactory(handle) as V
    }
  }).get(V::class.java)
}

inline fun <reified V : ViewModel> Fragment.obtainViewModelWithState(
  defaultArgs: Bundle?,
  crossinline viewModelFactory: (savedStateHandle: SavedStateHandle) -> V
): V {
  return ViewModelProviders.of(
    this,
    object : AbstractSavedStateViewModelFactory(this, defaultArgs) {
      override fun <V : ViewModel?> create(
        key: String,
        modelClass: Class<V>,
        handle: SavedStateHandle
      ): V {
        return viewModelFactory(handle) as V
      }
    }).get(V::class.java)
}