package cz.seznam.kommons.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/** Base fragment for implementing Views by Model View ViewModel pattern.
 *
 * Implementations have to provide view and model. Fragment itself is responsible for linking these two components
 * in the right time.
 *
 * @author Jakub Janda
 */
abstract class MVVMFragment<M : IViewModel, A : IViewActions> : Fragment() {
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
            val v = view.createView(obtainInflater(inflater), container, savedInstanceState)

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        view?.saveViewState(outState)
    }

    open fun obtainInflater(origInflater: LayoutInflater): LayoutInflater = origInflater
}
