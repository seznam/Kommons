package cz.seznam.kommons.kexts

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import cz.seznam.kommons.R

/**
 * @author Jakub Janda
 */

var View.visible: Boolean
	get() = visibility == View.VISIBLE
	set(value) {
		visibility = if (value) View.VISIBLE else View.GONE
	}

var View.invisible: Boolean
	get() = visibility == View.INVISIBLE
	set(value) {
		visibility = if (value) View.INVISIBLE else View.VISIBLE
	}

var View.scale: Float
	get() = scaleX
	set(value) {
		scaleX = value
		scaleY = value
	}

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.isInvisible(): Boolean = visibility == View.INVISIBLE

fun View.isGone(): Boolean = visibility == View.GONE

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

fun View.animTranslationX(from: Float = translationX,
													to: Float): Animator = setViewAnimation(this, ObjectAnimator.ofFloat(this,
																																															 "translationX",
																																															 from,
																																															 to))

fun View.animTranslationY(from: Float = translationY,
													to: Float): Animator = setViewAnimation(this, ObjectAnimator.ofFloat(this,
																																															 "translationY",
																																															 from,
																																															 to))

fun View.animAlpha(from: Float = alpha,
									 to: Float): Animator = setViewAnimation(this, ObjectAnimator.ofFloat(this, "alpha", from, to))

fun View.animateScale(from: Float = scale,
											to: Float): Animator {

	val anim = AnimatorSet()
	anim.playTogether(ObjectAnimator.ofFloat(this, "scaleX", from, to),
										ObjectAnimator.ofFloat(this, "scaleY", from, to))

	return setViewAnimation(this, anim)
}


fun View.createTranslationX(from: Float = this.translationX, to: Float): Animator = ObjectAnimator.ofFloat(this,
																																																					 "translationX",
																																																					 from,
																																																					 to)

fun View.createAnimTransY(from: Float = this.translationY, to: Float): Animator = ObjectAnimator.ofFloat(this,
																																																				 "translationY",
																																																				 from,
																																																				 to)

fun View.createAlphaAnim(from: Float = this.alpha, to: Float): Animator = ObjectAnimator.ofFloat(this,
																																																 "alpha",
																																																 from,
																																																 to)

fun View.createScaleAnim(from: Float = this.scale, to: Float): Animator {

	val anim = AnimatorSet()
	anim.playTogether(ObjectAnimator.ofFloat(this, "scaleX", from, to),
										ObjectAnimator.ofFloat(this, "scaleY", from, to))

	return anim
}

fun View.createRotateAnim(from: Float = this.rotation, to: Float): Animator = ObjectAnimator.ofFloat(this,
																																																		 "rotation",
																																																		 from,
																																																		 to)

fun View.clearAnim() {
	val oldAnim = getTag(R.id.view_animator) as? Animator?
	oldAnim?.cancel()
}

private fun setViewAnimation(view: View,
														 animator: Animator): Animator {
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
