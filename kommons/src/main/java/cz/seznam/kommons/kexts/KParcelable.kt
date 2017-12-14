package cz.seznam.kommons.kexts

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Jakub Janda
 */
interface KParcelable : Parcelable {
	override fun describeContents() = 0
	override fun writeToParcel(dest: Parcel,
														 flags: Int)
}

inline fun <reified T> parcelableCreator(
		crossinline create: (Parcel) -> T) =
		object : Parcelable.Creator<T> {
			override fun createFromParcel(source: Parcel) = create(source)
			override fun newArray(size: Int) = arrayOfNulls<T>(size)
		}

inline fun <reified T> parcelableClassLoaderCreator(
		crossinline create: (Parcel, ClassLoader) -> T) =
		object : Parcelable.ClassLoaderCreator<T> {
			override fun createFromParcel(source: Parcel,
																		loader: ClassLoader) = create(source, loader)

			override fun createFromParcel(source: Parcel) = createFromParcel(source, T::class.java.classLoader)

			override fun newArray(size: Int) = arrayOfNulls<T>(size)
		}

/** Reads boolean from parcel.
 *
 * Parcel itself is not capable of holding booleans, so the value is represented
 * as byte, where 1 is true, 0 false.
 *
 */
fun Parcel.readBoolean(): Boolean = readByte() == 1.toByte()

/** Writes boolean into parcel.
 *
 * Parcel itself is not capable of holding booleans, so the value is represented
 * as byte, where 1 is true, 0 false.
 *
 */
fun Parcel.writeBoolean(value: Boolean) = writeByte(if (value) 1 else 0)
