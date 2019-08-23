package cz.seznam.kommons.kexts

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.DimenRes
import cz.seznam.kommons.R

/** View is Visible or Gone
 */
var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

/** View is Invisible or Visible.
 */
var View.invisible: Boolean
    get() = visibility == View.INVISIBLE
    set(value) {
        visibility = if (value) View.INVISIBLE else View.VISIBLE
    }

/** View scale.
 *
 * Setter sets both scaleX and scaleY, getter gives scaleX.
 */
var View.scale: Float
    get() = scaleX
    set(value) {
        scaleX = value
        scaleY = value
    }

/** Return if view is visible.
 *
 * @return true if View.visibility == View.VISIBLE
 */
fun View.isVisible(): Boolean = visibility == View.VISIBLE

/** Return if view is invisible.
 *
 * @return true if View.visibility == View.INVISIBLE
 */
fun View.isInvisible(): Boolean = visibility == View.INVISIBLE

/** Return if view is gone.
 *
 * @return true if View.visibility == View.GONE
 */
fun View.isGone(): Boolean = visibility == View.GONE

/** Run single onPreDraw callback.
 *
 * @param callback callback when onPreDraw is called
 */
fun View.onSinglePreDraw(callback: () -> Unit) {
    val view = this
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            callback.invoke()
            view.viewTreeObserver.removeOnPreDrawListener(this)
            return false
        }
    })
}

/** Create animator of View.translationX and associate it with the view.
 *
 * Animator is saved as tag to the view.
 * Old associated animator is canceled.
 *
 * @param from start value, default is current translationX
 * @param to end value
 *
 * @return animator
 */
fun View.animTranslationX(
    from: Float = translationX,
    to: Float
): Animator = setViewAnimation(
    this, ObjectAnimator.ofFloat(
        this,
        "translationX",
        from,
        to
    )
)

/** Create animator of View.translationY and associate it with the view.
 *
 * Animator is saved as tag to the view.
 * Old associated animator is canceled.
 *
 * @param from start value, default is current translationY
 * @param to end value
 *
 * @return animator
 */
fun View.animTranslationY(
    from: Float = translationY,
    to: Float
): Animator = setViewAnimation(
    this, ObjectAnimator.ofFloat(
        this,
        "translationY",
        from,
        to
    )
)

/** Create animator of View.alpha and associate it with the view.
 *
 * Animator is saved as tag to the view.
 * Old associated animator is canceled.
 *
 * @param from start value, default is current alpha
 * @param to end value
 *
 * @return animator
 */
fun View.animAlpha(
    from: Float = alpha,
    to: Float
): Animator = setViewAnimation(this, ObjectAnimator.ofFloat(this, "alpha", from, to))

/** Create animator of View.scaleX and View.scaleY and associate it with the view.
 *
 * Animator is saved as tag to the view.
 * Old associated animator is canceled.
 *
 * @param from start value, default is current scaleX
 * @param to end value
 *
 * @return animator
 */
fun View.animateScale(
    from: Float = scale,
    to: Float
): Animator {

    val anim = AnimatorSet()
    anim.playTogether(
        ObjectAnimator.ofFloat(this, "scaleX", from, to),
        ObjectAnimator.ofFloat(this, "scaleY", from, to)
    )

    return setViewAnimation(this, anim)
}


fun View.animateRotation(
    from: Float = rotation,
    to: Float
): Animator {
    return setViewAnimation(this, ObjectAnimator.ofFloat(this, "rotation", from, to))
}

fun View.createTranslationX(from: Float = this.translationX, to: Float): Animator =
    ObjectAnimator.ofFloat(
        this,
        "translationX",
        from,
        to
    )

fun View.createAnimTransY(from: Float = this.translationY, to: Float): Animator =
    ObjectAnimator.ofFloat(
        this,
        "translationY",
        from,
        to
    )

fun View.createAlphaAnim(from: Float = this.alpha, to: Float): Animator = ObjectAnimator.ofFloat(
    this,
    "alpha",
    from,
    to
)

fun View.createScaleAnim(from: Float = this.scale, to: Float): Animator {

    val anim = AnimatorSet()
    anim.playTogether(
        ObjectAnimator.ofFloat(this, "scaleX", from, to),
        ObjectAnimator.ofFloat(this, "scaleY", from, to)
    )

    return anim
}

fun View.createRotateAnim(from: Float = this.rotation, to: Float): Animator =
    ObjectAnimator.ofFloat(
        this,
        "rotation",
        from,
        to
    )

fun View.clearAnim() {
    val oldAnim = getTag(R.id.view_animator) as? Animator?
    oldAnim?.cancel()
}

private fun setViewAnimation(
    view: View,
    animator: Animator
): Animator {
    val oldAnim = view.getTag(R.id.view_animator) as? Animator?
    oldAnim?.cancel()
    (view.getTag(R.id.view_animator) as? Animator?)?.cancel()
    view.setTag(R.id.view_animator, animator)
    animator.onEnd { view.setTag(R.id.view_animator, null) }
    return animator
}

fun View.setPaddingLeft(padding: Int) = setPadding(padding, paddingTop, paddingRight, paddingBottom)

fun View.setPaddingTop(padding: Int) = setPadding(paddingLeft, padding, paddingRight, paddingBottom)

var View.topMargin: Int
    get() {
        val lp = layoutParams as ViewGroup.MarginLayoutParams
        return lp.topMargin
    }
    set(value) {
        val lp = layoutParams as ViewGroup.MarginLayoutParams
        lp.topMargin = value
        requestLayout()
    }

var View.bottomMargin: Int
    get() {
        val lp = layoutParams as ViewGroup.MarginLayoutParams
        return lp.bottomMargin
    }
    set(value) {
        val lp = layoutParams as ViewGroup.MarginLayoutParams
        lp.bottomMargin = value
        requestLayout()
    }

var View.rightMargin: Int
    get() {
        val lp = layoutParams as ViewGroup.MarginLayoutParams
        return lp.rightMargin
    }
    set(value) {
        val lp = layoutParams as ViewGroup.MarginLayoutParams
        lp.rightMargin = value
        requestLayout()
    }

var View.leftMargin: Int
    get() {
        val lp = layoutParams as ViewGroup.MarginLayoutParams
        return lp.leftMargin
    }
    set(value) {
        val lp = layoutParams as ViewGroup.MarginLayoutParams
        lp.leftMargin = value
        requestLayout()
    }

fun View.px(@DimenRes dimenRes: Int): Int = resources.getDimensionPixelSize(dimenRes)

fun View.dip(pixels: Int): Float = resources.displayMetrics.scaledDensity * pixels