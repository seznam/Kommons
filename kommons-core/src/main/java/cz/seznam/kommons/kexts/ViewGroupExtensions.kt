package cz.seznam.kommons.kexts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/** Inflate view into the ViewGroup.
 *
 * @param layoutResId layout resource id
 * @param attachToParent if attach created view to the ViewGroup, default true
 *
 * @return inflated view
 *
 * @author Jakub Janda
 */
fun ViewGroup.inflate(
    layoutResId: Int,
    attachToParent: Boolean = true
                     ): View {
    val inflater = LayoutInflater.from(this.context)
    val view = inflater.inflate(layoutResId, this, false)

    if (attachToParent) {
        addView(view)
    }

    return view
}

/** Return children of the ViewGroup
 *
 * @return children
 */
val ViewGroup.children: Array<View>
    get () = Array(this.childCount) { i -> this.getChildAt(i) }
