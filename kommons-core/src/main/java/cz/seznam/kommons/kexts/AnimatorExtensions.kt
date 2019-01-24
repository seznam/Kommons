package cz.seznam.kommons.kexts

import android.animation.Animator

/** Sets animator started callback.
 *
 * @param onStart callback invoked when the animator is started.
 *
 * @return instance of the animator
 *
 * @author Jakub Janda
 */
fun Animator.onStart(onStart: () -> Unit): Animator {
    this.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) = Unit

        override fun onAnimationEnd(animation: Animator?) = Unit

        override fun onAnimationCancel(animation: Animator?) = Unit

        override fun onAnimationStart(animation: Animator?) = onStart()
    })

    return this
}

/** Sets animator ended callback.
 *
 * Callback takes one parameter, which indicates, if the animator was cancelled.
 *
 * @param onEnd callback invoked when the animator is ended
 *
 * @return instance of the animator
 *
 * @author Jakub Janda
 */
fun Animator.onEnd(onEnd: (canceled: Boolean) -> Unit): Animator {
    this.addListener(object : Animator.AnimatorListener {
        var canceled = false

        override fun onAnimationRepeat(animation: Animator?) = Unit

        override fun onAnimationEnd(animation: Animator?) = onEnd(canceled)

        override fun onAnimationCancel(animation: Animator?) {
            canceled = true
        }

        override fun onAnimationStart(animation: Animator?) = Unit
    })

    return this
}
