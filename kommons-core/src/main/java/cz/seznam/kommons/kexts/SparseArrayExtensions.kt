package cz.seznam.kommons.kexts

import android.util.SparseArray

/** Sparse array values.
 *
 * @author Jakub Janda
 */
inline val <reified T> SparseArray<T>.values: Array<T>
    get() = Array(this.size()) { this.valueAt(it) }

/** Set value on key operator.
 *
 * Ex. set[5] = value
 *
 * @param key key
 * @param value
 */
operator fun <T> SparseArray<T>.set(key: Int, value: T) {
    put(key, value)
}

