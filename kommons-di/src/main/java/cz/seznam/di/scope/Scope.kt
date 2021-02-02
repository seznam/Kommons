package cz.seznam.di.scope

import cz.seznam.di.instance.AbstractInstanceFactory

/**
 * @author Jakub Janda
 */
@Suppress("UNCHECKED_CAST")
class Scope(
  private val definitions: List<ScopeDefinition>,
  private val parent: Scope? = null,
  internal val parameters: ScopeParameters = ScopeParameters()
) {
  internal val instanceMap: MutableMap<String, Any> = mutableMapOf()

  fun <T> obtain(clazz: Class<T>, specialization: String = ""): T {
    val key = "${clazz.name}_$specialization"
    var factory: AbstractInstanceFactory<*>? = null

    for (def in definitions) {
      factory = def.findFactory(key)
      if (factory != null) {
        break
      }
    }

    return if (factory != null) {
      factory.obtain(this, parameters) as T
    } else {
      val parent = parent

      if (parent == null) {
        throw RuntimeException("There is no parent of ${definitions[0].name} with factory for $key")
      } else {
        parent.obtain(clazz, specialization)
          ?: throw RuntimeException("Parent ${parent.definitions[0].name} can't create instance of $key")
      }
    }
  }

  fun release() {
    instanceMap.clear()
  }
}

inline fun <reified T> Scope.obtain(specialization: String = ""): T =
  obtain(T::class.java, specialization)