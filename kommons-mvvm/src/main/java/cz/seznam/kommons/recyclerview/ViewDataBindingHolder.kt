package cz.seznam.kommons.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/** Implementation of RecyclerView.ViewHolder holding databinding instance.
 * @author Jakub Janda
 */
class ViewDataBindingHolder<out T : ViewDataBinding>(val viewBinding: T) :
    RecyclerView.ViewHolder(viewBinding.root)
