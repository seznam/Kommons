package cz.seznam.di.instance

import cz.seznam.di.scope.Scope
import cz.seznam.di.scope.ScopeParameters

class NewInstanceFactory<T>(
    clazz: Class<T>,
    private val creator: Scope.(params: ScopeParameters) -> T
) : AbstractInstanceFactory<T>(clazz) {

    override fun obtain(scope: Scope, parameters: ScopeParameters): T {
        return creator.invoke(scope, parameters)
    }
}