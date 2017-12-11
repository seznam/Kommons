package cz.kubajanda.kommons.kexts

import android.content.Intent

/**
 * @author Jakub Janda
 */

fun Intent.withAction(action: String): Intent {
	this.action = action
	return this
}
