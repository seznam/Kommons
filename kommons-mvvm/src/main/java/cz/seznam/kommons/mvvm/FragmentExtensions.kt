package cz.seznam.kommons.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * @author Jakub Janda
 */

inline fun <reified V : ViewModel> androidx.fragment.app.Fragment.obtainViewModel(crossinline viewModelFactory: () -> V): V {
    return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
        override fun <V : ViewModel?> create(modelClass: Class<V>): V {
            return viewModelFactory() as V
        }
    }).get(V::class.java)
}
