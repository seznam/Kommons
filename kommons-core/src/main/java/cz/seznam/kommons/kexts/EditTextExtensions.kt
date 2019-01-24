package cz.seznam.kommons.kexts

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/** Add on text change callback to EditText view.
 *
 * @param listener callback which is called when text is changed
 *
 * @author Jakub Janda
 */
inline fun EditText.onTextChanged(crossinline listener: (query: String) -> Unit) =
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
                                      ) = Unit

        override fun onTextChanged(
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
                                  ) = listener(s.toString())
    })

/** Show soft input for this edit text.
 *
 */
fun EditText.openKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}