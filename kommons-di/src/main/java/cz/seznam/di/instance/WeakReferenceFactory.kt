package cz.seznam.di.instance

import cz.seznam.di.scope.Scope
import cz.seznam.di.scope.ScopeParameters
import java.lang.ref.WeakReference

class WeakReferenceFactory<T>(
    clazz: Class<T>,
    private val creator: Scope.(ScopeParameters) -> T
) : AbstractInstanceFactory<T>(clazz) {

    private val lock = this

    override fun obtain(scope: Scope, params: ScopeParameters): T {
        val i = findReference(scope)?.get()

        if (i != null) {
            return i
        } else {
            synchronized(lock) {
                var i2 = findReference(scope)?.get()
                if (i2 != null) {
                    return i2
                } else {
                    i2 = creator.invoke(scope, params)
                    saveReference(scope, i2)
                    return i2
                }
            }
        }
    }

    private fun findReference(scope: Scope): WeakReference<T>? {
        return scope.instanceMap[clazz.name] as WeakReference<T>?
    }

    private fun saveReference(scope: Scope, instance: T) {
        scope.instanceMap[clazz.name] = WeakReference<T>(instance) as Any
    }
}