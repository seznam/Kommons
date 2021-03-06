package cz.seznam.kommons.kexts

import android.os.Bundle
import androidx.fragment.app.Fragment

/** Set arguments for this fragment instance.
 *
 * ~~~~~
 *
 * Fragment().withArgs {
 * 	putString("arg1", "Answer is ")
 * 	putInt(42)
 * }
 *
 * ~~~~~
 *
 * @param applyArgs callback with argument set.
 *
 * @return fragment instance
 *
 * @author Jakub Janda
 */
fun <T : Fragment> T.withArgs(applyArgs: Bundle.() -> Unit): T {
    val args = Bundle()
    applyArgs(args)
    this.arguments = args

    return this
}