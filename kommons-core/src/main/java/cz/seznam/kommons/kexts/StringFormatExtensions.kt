package cz.seznam.kommons.kexts

/** Format double as string.
 *
 * @param digits digit count
 *
 * @author Jakub Janda
 */
fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)
