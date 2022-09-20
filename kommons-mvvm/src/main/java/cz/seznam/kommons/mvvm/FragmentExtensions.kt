package cz.seznam.kommons.mvvm

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/** Extension for obtaining viewmodel from fragment as viewmodel store owner.
 *
 * It uses ViewModelProvider with this fragment as owner and given callback
 * for creating instance of viewModel.
 *
 * @param viewModelFactory factory method for creating viewModel
 */
inline fun <reified V : ViewModel> Fragment.obtainViewModel(crossinline viewModelFactory: () -> V): V {
  return ViewModelProvider(this, object : ViewModelProvider.Factory {
    override fun <V : ViewModel> create(modelClass: Class<V>): V {
      return viewModelFactory() as V
    }

  })[V::class.java]
}

/** Extension for obtaining viewmodel from fragment as viewmodel store owner with state handle.
 *
 * It uses ViewModelProvider with this fragment as owner and given callback
 * for creating instance of viewModel.
 *
 * @param defaultArgs default arguments passed to the viewmodel
 * @param viewModelFactory factory method for creating viewModel with provided saved state of viewmodel
 */
inline fun <reified V : ViewModel> Fragment.obtainViewModelWithState(
  defaultArgs: Bundle? = null,
  crossinline viewModelFactory: (savedStateHandle: SavedStateHandle) -> V
): V {
  return ViewModelProvider(this, object : AbstractSavedStateViewModelFactory(this, defaultArgs) {
    override fun <V : ViewModel> create(
      key: String,
      modelClass: Class<V>,
      handle: SavedStateHandle
    ): V {
      return viewModelFactory(handle) as V
    }
  })[V::class.java]
}