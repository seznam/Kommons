package cz.seznam.kommons.kexts

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.StringRes
import cz.seznam.kommons.utils.TintUtils

/**
 * @author Jakub Janda
 */

/** Set top drawable.
 *
 * @param drawableRes drawable resource id
 * @param colorRes tint color resourceId
 *
 * @author Jakub Janda
 */
fun TextView.setDrawableTop(
	drawableRes: Int,
	colorRes: Int = 0
													 ) = if (colorRes == 0) {
	setCompoundDrawablesWithIntrinsicBounds(0, drawableRes, 0, 0)
} else {
	setCompoundDrawablesWithIntrinsicBounds(
		null, TintUtils.getTintedDrawable(
			context,
			drawableRes,
			colorRes,
			true
																		 ), null, null
																				 )
}

/** Set bottom drawable.
 *
 * @param drawableRes drawable resource id
 * @param colorRes tint color resourceId
 *
 * @author Jakub Janda
 */
fun TextView.setDrawableBottom(
	drawableRes: Int,
	colorRes: Int = 0
															) = if (colorRes == 0) {
	setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, drawableRes)
} else {
	setCompoundDrawablesWithIntrinsicBounds(
		null, null, null, TintUtils.getTintedDrawable(
			context,
			drawableRes,
			colorRes,
			true
																								 )
																				 )
}

/** Set right drawable.
 *
 * @param drawableRes drawable resource id
 * @param colorRes tint color resourceId
 *
 * @author Jakub Janda
 */
fun TextView.setDrawableRight(
	drawableRes: Int,
	colorRes: Int = 0
														 ) = if (colorRes == 0) {
	setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRes, 0)
} else {
	setCompoundDrawablesWithIntrinsicBounds(
		null, null, TintUtils.getTintedDrawable(
			context,
			drawableRes,
			colorRes,
			true
																					 ), null
																				 )
}

/** Set left drawable.
 *
 * @param drawableRes drawable resource id
 * @param colorRes tint color resourceId
 *
 * @author Jakub Janda
 */
fun TextView.setDrawableLeft(
	drawableRes: Int,
	colorRes: Int = 0
														) = if (colorRes == 0) {
	setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRes, 0)
} else {
	setCompoundDrawablesWithIntrinsicBounds(
		TintUtils.getTintedDrawable(
			context,
			drawableRes,
			colorRes,
			true
															 ), null, null, null
																				 )
}

/** Set left drawable.
 *
 * @param drawable drawable
 *
 * @author Jakub Janda
 */
fun TextView.setDrawableLeft(drawable: Drawable?) = setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

/** Set top drawable.
 *
 * @param drawable drawable
 *
 * @author Jakub Janda
 */
fun TextView.setDrawableTop(drawable: Drawable?) = setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)

/** Set right drawable.
 *
 * @param drawable drawable
 *
 * @author Jakub Janda
 */
fun TextView.setDrawableRight(drawable: Drawable?) = setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

/** Set bottom drawable.
 *
 * @param drawable drawable
 *
 * @author Jakub Janda
 */
fun TextView.setDrawableBottom(drawable: Drawable?) = setCompoundDrawablesWithIntrinsicBounds(
	null,
	null,
	null,
	drawable
																																														 )

/** Set text with format arguments to the TextView.
 *
 * @param resId string resource id
 * @param args format arguments
 */
fun TextView.setText(@StringRes resId: Int, vararg args: Any) {
	text = context.getString(resId, *args)
}
