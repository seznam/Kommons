package cz.seznam.kommons.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

object TintUtils {
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
