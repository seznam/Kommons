package cz.seznam.kommons.recyclerview

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * @author Jakub Janda
 */
class ViewDataBindingHolder<out T : ViewDataBinding>(val viewBinding: T) : RecyclerView.ViewHolder(viewBinding.root)
