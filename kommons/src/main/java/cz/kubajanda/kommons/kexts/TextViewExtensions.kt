package cz.kubajanda.kommons.kexts

import android.graphics.drawable.Drawable
import android.widget.TextView
import cz.kubajanda.kommons.utils.TintUtils

/**
 * @author Jakub Janda
 */

/** Ext. funkce - rozsiruje TextView o moznost nastavit top drawable s tint barvou.
 *
 */
fun TextView.setDrawableTop(drawableRes: Int,
														colorRes: Int = 0) = if (colorRes == 0) {
	setCompoundDrawablesWithIntrinsicBounds(0, drawableRes, 0, 0)
} else {
	setCompoundDrawablesWithIntrinsicBounds(null, TintUtils.getTintedDrawable(context,
																																						drawableRes,
																																						colorRes,
																																						true), null, null)
}

fun TextView.setDrawableBottom(drawableRes: Int,
															 colorRes: Int = 0) = if (colorRes == 0) {
	setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, drawableRes)
} else {
	setCompoundDrawablesWithIntrinsicBounds(null, null, null, TintUtils.getTintedDrawable(context,
																																												drawableRes,
																																												colorRes,
																																												true))
}

fun TextView.setDrawableRight(drawableRes: Int,
															colorRes: Int = 0) = if (colorRes == 0) {
	setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRes, 0)
} else {
	setCompoundDrawablesWithIntrinsicBounds(null, null, TintUtils.getTintedDrawable(context,
																																									drawableRes,
																																									colorRes,
																																									true), null)
}

fun TextView.setDrawableLeft(drawableRes: Int,
														 colorRes: Int = 0) = if (colorRes == 0) {
	setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRes, 0)
} else {
	setCompoundDrawablesWithIntrinsicBounds(TintUtils.getTintedDrawable(context,
																																			drawableRes,
																																			colorRes,
																																			true), null, null, null)
}

fun TextView.setDrawableLeft(drawable: Drawable) = setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
