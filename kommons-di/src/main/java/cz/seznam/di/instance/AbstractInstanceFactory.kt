package cz.seznam.di.instance

import cz.seznam.di.scope.Scope
import cz.seznam.di.scope.ScopeParameters

/**
 * @author Jakub Janda
 */
abstract class AbstractInstanceFactory<T>(val clazz: Class<T>) {
    abstract fun obtain(scope: Scope, parameters: ScopeParameters): T
}