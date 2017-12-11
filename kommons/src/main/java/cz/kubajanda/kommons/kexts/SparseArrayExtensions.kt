package cz.kubajanda.kommons.kexts

import android.util.SparseArray

/**
 * @author Jakub Janda
 */
inline val <reified T> SparseArray<T>.values: Array<T>
	get() = Array(this.size(), { this.valueAt(it) })
