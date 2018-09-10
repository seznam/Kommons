package cz.seznam.kommons.recyclerview

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import cz.seznam.kommons.mvvm.BR
import cz.seznam.kommons.mvvm.IViewActions

/**
 * @author Jakub Janda
 */
abstract class DataBindingRecyclerAdapter<T, H : ViewDataBinding>(context: Context,
																																	itemCallbacks: DiffUtil.ItemCallback<T> = EmptyDataBindingItemCallback()) :
		ListAdapter<T, ViewDataBindingHolder<H>>(itemCallbacks) {
	private val data = ArrayList<T>()
	val layoutInflater: LayoutInflater = LayoutInflater.from(context)

	open fun clear() {
		data.clear()

		submitList(ArrayList(data))
	}

	open fun add(item: T, position: Int = -1) {
		if (position >= 0) {
			data.add(position, item)
		} else {
			data.add(item)
		}

		submitList(ArrayList(data))
	}

	open fun add(items: Collection<T>) {
		this.data.addAll(items)

		submitList(ArrayList(data))
	}

	open fun set(items: Collection<T>) {
		this.data.clear()
		data.addAll(items)

		submitList(ArrayList(data))
	}

	open fun remove(item: T) {
		val index = data.indexOf(item)

		if (index > -1) {
			data.remove(item)

			submitList(ArrayList(data))
		}
	}

	public override fun getItem(position: Int): T {
		return super.getItem(position)
	}

	fun getPosition(item: T): Int {
		return data.indexOf(item)
	}

	override fun onCreateViewHolder(parent: ViewGroup,
																	viewType: Int): ViewDataBindingHolder<H> = ViewDataBindingHolder(onCreateView(parent,
																																																								viewType))

	open fun onCreateView(parent: ViewGroup,
												viewType: Int): H = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)

	override fun onBindViewHolder(holder: ViewDataBindingHolder<H>, position: Int) {
		val viewModel = getItem(position)
		holder.viewBinding.setVariable(BR.viewModel, viewModel)
		holder.viewBinding.setVariable(BR.viewActions, getViewActions(position))
		holder.viewBinding.executePendingBindings()
	}


	override fun getItemViewType(position: Int): Int = getViewResource(position)

	abstract fun getViewResource(position: Int): Int

	open fun getViewActions(position: Int): IViewActions? = null

	class EmptyDataBindingItemCallback<T> : DiffUtil.ItemCallback<T>() {
		override fun areItemsTheSame(oldItem: T, newItem: T) = false

		override fun areContentsTheSame(oldItem: T, newItem: T) = false
	}
}
