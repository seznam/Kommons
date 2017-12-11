package cz.kubajanda.kommons.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

/**
 * @author Jakub Janda
 */
object TintUtils {

	/** Vrati obarveny drawable objekt.
	 *
	 * Pokud je parametr copy false, obarvuje se drawable vraceny primo metodou Resources.getDrawable,
	 * tim padem muzou byt obarveny vsechny drawable objekty, ktere jsou vytvoreny z daneho id.
	 *
	 * Pokud je parametr copy true, je nedrive drawable zkopirovan a pote az obarven, aby se zamezilo nechtenemu
	 * obarveni ostatnich drawable objektu z daneho resource id.
	 *
	 * @param context context
	 * @param iconRes resource id drawable obrazku
	 * @param colorRes resource id barvy, kterou chceme obarvovat
	 * @param copy zda pred aplikovanim tintu nejdrive zkopirovat drawable
	 *
	 * @return obarveny drawable
	 */
	@JvmOverloads
	fun getTintedDrawable(context: Context,
												iconRes: Int,
												colorRes: Int,
												copy: Boolean = false): Drawable {
		val res = context.resources
		val color = res.getColor(colorRes)

		return getTintedDrawableByColor(context, iconRes, color, copy)
	}

	/** Vrati obarveny drawable objekt.
	 *
	 * Pokud je parametr copy false, obarvuje se drawable vraceny primo metodou Resources.getDrawable,
	 * tim padem muzou byt obarveny vsechny drawable objekty, ktere jsou vytvoreny z daneho id.
	 *
	 * Pokud je parametr copy true, je nedrive drawable zkopirovan a pote az obarven, aby se zamezilo nechtenemu
	 * obarveni ostatnich drawable objektu z daneho resource id.
	 *
	 * @param context context
	 * @param iconRes resource id drawable obrazku
	 * @param colorValue hodnota barvy (to co vrati volani resources.getColor()), kterou chceme obarvovat
	 * @param copy zda pred aplikovanim tintu nejdrive zkopirovat drawable
	 *
	 * @return obarveny drawable
	 */
	fun getTintedDrawableByColor(context: Context,
															 iconRes: Int,
															 colorValue: Int,
															 copy: Boolean): Drawable {
		var drawable = ContextCompat.getDrawable(context, iconRes)

		if (copy) {
			drawable = drawable!!.mutate()
		}

		drawable!!.setColorFilter(colorValue, PorterDuff.Mode.SRC_ATOP)

		return drawable
	}

	fun getTintedDrawable(context: Context,
												drawable: Drawable,
												colorRes: Int,
												copy: Boolean): Drawable {
		var drawable = drawable
		val res = context.resources
		val color = res.getColor(colorRes)

		if (copy) {
			drawable = drawable.mutate()
		}

		drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)

		return drawable
	}

	fun getTintedDrawableByColor(context: Context,
															 drawable: Drawable,
															 color: Int,
															 copy: Boolean): Drawable {
		var drawable = drawable
		if (copy) {
			drawable = drawable.mutate()
		}

		drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)

		return drawable
	}
}
