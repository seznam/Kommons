package cz.seznam.kommons.kexts

import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.DimenRes

/** Set left drawable.
 *
 * @param drawable drawable
 *
 * @author Jakub Janda
 */
fun TextView.setDrawableLeft(drawable: Drawable?) =
  setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)


fun TextView.setTextSizePx(@DimenRes dimen: Int) {
  setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(dimen).toFloat())
}
