package cz.seznam.kommons.kexts

/**
 * @author Jakub Janda
 */
fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)
