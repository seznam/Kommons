package cz.seznam.kommons.recyclerview

import androidx.databinding.ViewDataBinding

/**
 * @author Jakub Janda
 */
class ViewDataBindingHolder<out T : ViewDataBinding>(val viewBinding: T) :
	androidx.recyclerview.widget.RecyclerView.ViewHolder(viewBinding.root)
