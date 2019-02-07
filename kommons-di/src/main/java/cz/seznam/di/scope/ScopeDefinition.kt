package cz.seznam.di.scope

import android.util.Log
import cz.seznam.di.instance.AbstractInstanceFactory
import cz.seznam.di.instance.NewInstanceFactory
import cz.seznam.di.instance.SingletonFactory
import cz.seznam.di.instance.WeakReferenceFactory

/**
 * @author Jakub Janda
 */
@Suppress("UNCHECKED_CAST")
open class ScopeDefinition(val name: String) {
    private val factoryMap: MutableMap<String, AbstractInstanceFactory<*>> = mutableMapOf()

    fun findFactory(key: String): AbstractInstanceFactory<*>? {
        return factoryMap[key]
    }

    fun registerInstanceFactory(
        instanceFactory: AbstractInstanceFactory<*>,
        specialization: String = "",
        override: Boolean = false
    ) {
        val key = "${instanceFactory.clazz.name}_$specialization"

        Log.i("ScopeDefinition", "Registring $key")

        if (factoryMap.containsKey(key) && !override) {
            throw RuntimeException("Instance factory for $key already registered. Check your dependency tree or use override")
        }

        factoryMap[key] = instanceFactory
    }

    inline fun <reified T> factory(noinline creator: Scope.(ScopeParameters) -> T) {
        registerInstanceFactory(NewInstanceFactory(T::class.java, creator))
    }

    inline fun <reified T> singleton(noinline creator: Scope.(ScopeParameters) -> T) {
        registerInstanceFactory(SingletonFactory(T::class.java, creator))
    }

    inline fun <reified T> weakReference(noinline creator: Scope.(ScopeParameters) -> T) {
        registerInstanceFactory(WeakReferenceFactory(T::class.java, creator))
    }
}