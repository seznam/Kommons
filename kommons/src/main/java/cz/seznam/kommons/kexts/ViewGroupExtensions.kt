package cz.seznam.kommons.kexts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author Jakub Janda
 */


fun ViewGroup.inflate(layoutResId: Int,
					  attachToParent: Boolean = true) {
	val inflater = LayoutInflater.from(this.context)
	val view = inflater.inflate(layoutResId, this, attachToParent)
}

val ViewGroup.children: Array<View>
	get () = Array(this.childCount, { i -> this.getChildAt(i) })
