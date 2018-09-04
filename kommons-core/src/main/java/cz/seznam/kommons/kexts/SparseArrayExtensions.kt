package cz.seznam.kommons.kexts

import android.util.SparseArray

/**
 * @author Jakub Janda
 */
inline val <reified T> SparseArray<T>.values: Array<T>
	get() = Array(this.size()) { this.valueAt(it) }

operator fun <T> SparseArray<T>.set(key: Int, value: T) {
	put(key, value)
}

