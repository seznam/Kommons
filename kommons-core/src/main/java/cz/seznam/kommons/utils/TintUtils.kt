package cz.seznam.kommons.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

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
    fun getTintedDrawable(
        context: Context,
        @DrawableRes iconRes: Int,
        @ColorRes colorRes: Int,
        copy: Boolean = false
                         ): Drawable {
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
    fun getTintedDrawableByColor(
        context: Context,
        @DrawableRes iconRes: Int,
        @ColorInt colorValue: Int,
        copy: Boolean
                                ): Drawable {
        var drawable = ContextCompat.getDrawable(context, iconRes)

        if (copy) {
            drawable = drawable!!.mutate()
        }

        drawable!!.setColorFilter(colorValue, PorterDuff.Mode.SRC_ATOP)

        return drawable
    }

    fun getTintedDrawable(
        context: Context,
        drawable: Drawable,
        @ColorRes colorRes: Int,
        copy: Boolean
                         ): Drawable {
        var drawable = drawable
        val res = context.resources
        val color = res.getColor(colorRes)

        if (copy) {
            drawable = drawable.mutate()
        }

        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)

        return drawable
    }

    fun getTintedDrawableByColor(
        drawable: Drawable,
        @ColorInt color: Int,
        copy: Boolean
                                ): Drawable {
        var drawable = drawable
        if (copy) {
            drawable = drawable.mutate()
        }

        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)

        return drawable
    }
}
