package cz.seznam.kommons.recyclerview

import android.content.Context
import android.databinding.ViewDataBinding
import cz.seznam.kommons.mvvm.IViewActions
import cz.seznam.mapy.widget.DataBindingRecyclerAdapter

/**
 * @author Jakub Janda
 */
class SimpleRecyclerAdapter<T>(context: Context,
															 private val resourceId: Int,
															 private val viewActions: IViewActions? = null) : DataBindingRecyclerAdapter<T, ViewDataBinding>(
		context) {
	override fun getViewResource(position: Int): Int = resourceId

	override fun getViewActions(position: Int): IViewActions? = viewActions
}
