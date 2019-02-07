package cz.seznam.di.scope

/**
 * @author Jakub Janda
 */
@Suppress("UNCHECKED_CAST")
class ScopeParameters(vararg params: Any?) {
    private val params = params.toList()

    operator fun <T> get(i: Int) = params[i] as T
}