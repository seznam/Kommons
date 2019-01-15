package cz.seznam.kommons.mvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment

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
