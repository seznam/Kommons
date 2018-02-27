package cz.seznam.mapy.widget

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import cz.seznam.kommons.mvvm.BR
import cz.seznam.kommons.mvvm.IViewActions

/**
 * @author Jakub Janda
 */
abstract class DataBindingRecyclerAdapter<T, H : ViewDataBinding>(val context: Context) : RecyclerView.Adapter<ViewDataBindingHolder<H>>() {
	private val data = ArrayList<T>()
	val layoutInflater: LayoutInflater = LayoutInflater.from(context)

	val items: List<T> = data

	open fun clear() {
		data.clear()
		notifyDataSetChanged()
	}

	open fun add(item: T, position: Int = -1) {
		if (position >= 0) {
			data.add(position, item)
			notifyItemInserted(position)
		} else {
			data.add(item)
			notifyItemInserted(data.size - 1)
		}
	}

	open fun add(items: Collection<T>) {
		this.data.addAll(items)
		notifyDataSetChanged()
	}

	open fun remove(item: T) {
		val index = data.indexOf(item)

		if (index > -1) {
			data.remove(item)
			notifyItemRemoved(index)
		}
	}

	override fun getItemCount() = data.size

	override fun onCreateViewHolder(parent: ViewGroup,
																	viewType: Int): ViewDataBindingHolder<H> = ViewDataBindingHolder(onCreateView(parent,
																																																								viewType))

	open fun onCreateView(parent: ViewGroup,
												viewType: Int): H = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)

	override fun onBindViewHolder(holder: ViewDataBindingHolder<H>?, position: Int) {
		val viewModel = data[position]
		holder?.view?.setVariable(BR.viewModel, viewModel)
		holder?.view?.setVariable(BR.viewActions, getViewActions(position))
		holder?.view?.executePendingBindings()
	}

	override fun getItemViewType(position: Int): Int = getViewResource(position)

	abstract fun getViewResource(position: Int): Int

	open fun getViewActions(position: Int): IViewActions? = null
}
