package cz.seznam.di.scope

/**
 * @author Jakub Janda
 */
@Suppress("UNCHECKED_CAST")
class Scope(
    private val definition: ScopeDefinition,
    private val parent: Scope? = null,
    internal val parameters: ScopeParameters = ScopeParameters()
) {
    internal val instanceMap: MutableMap<String, Any> = mutableMapOf()

    fun <T> obtain(clazz: Class<T>, specialization: String = ""): T {
        val key = "${clazz.name}_$specialization"
        val factory = definition.findFactory(key)
        return if (factory != null) {
            factory.obtain(this, parameters) as T
        } else {
            parent?.obtain(clazz, specialization) ?: throw RuntimeException("There is no factory for $key")
        }
    }

    fun release() {

    }
}

inline fun <reified T> Scope.obtain(specialization: String = ""): T = obtain(T::class.java, specialization)