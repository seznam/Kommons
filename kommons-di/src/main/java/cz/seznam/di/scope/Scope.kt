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
            val parent = parent

            if (parent == null) {
                throw RuntimeException("There is no parent of ${definition.name} with factory for $key")
            }
            else {
                parent.obtain(clazz, specialization) ?:
                throw RuntimeException("Parent ${parent.definition.name} can't create instance of $key")
            }
        }
    }

    fun release() {

    }
}

inline fun <reified T> Scope.obtain(specialization: String = ""): T = obtain(T::class.java, specialization)