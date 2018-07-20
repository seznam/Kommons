package cz.seznam.kommons.kexts

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * @author Jakub Janda
 */

fun <T : Fragment> T.withArgs(applyArgs: Bundle.() -> Unit): T {
	val args = Bundle()
	applyArgs(args)
	this.arguments = args

	return this
}