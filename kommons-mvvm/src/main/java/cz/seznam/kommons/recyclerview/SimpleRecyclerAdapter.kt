package cz.seznam.kommons.recyclerview

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import cz.seznam.kommons.mvvm.IViewActions

/**
 * @author Jakub Janda
 */
open class SimpleRecyclerAdapter<T>(
	context: Context,
	private val resourceId: Int,
	private val viewActions: IViewActions? = null,
	itemCallback: DiffUtil.ItemCallback<T> = EmptyDataBindingItemCallback()
																	 ) :
	DataBindingRecyclerAdapter<T, ViewDataBinding>(context, itemCallback) {
	override fun getViewResource(position: Int): Int = resourceId

	override fun getViewActions(position: Int): IViewActions? = viewActions
}
