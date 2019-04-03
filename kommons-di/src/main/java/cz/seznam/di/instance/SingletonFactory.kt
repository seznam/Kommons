package cz.seznam.di.instance

import cz.seznam.di.scope.Scope
import cz.seznam.di.scope.ScopeParameters

class SingletonFactory<T>(
    clazz: Class<T>,
    private val creator: Scope.(ScopeParameters) -> T
) : AbstractInstanceFactory<T>(clazz) {

    private val lock = this

    override fun obtain(scope: Scope, params: ScopeParameters): T {
        val i = findInstance(scope)
        if (i != null) {
            return i
        } else {
            synchronized(lock) {
                var i2 = findInstance(scope)

                if (i2 != null) {
                    return i2
                } else {
                    i2 = creator.invoke(scope, params)
                    saveInstance(scope, i2)
                    return i2
                }
            }
        }
    }

    private fun findInstance(scope: Scope): T? {
        return scope.instanceMap[clazz.name] as? T
    }

    private fun saveInstance(scope: Scope, instance: T) {
        scope.instanceMap[clazz.name] = instance as Any
    }
}