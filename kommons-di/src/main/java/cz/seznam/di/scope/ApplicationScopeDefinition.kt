package cz.seznam.di.scope

import android.app.Application

/**
 * @author Jakub Janda
 */
class ApplicationScopeDefinition<T : Application>(clazz: Class<T>) : ScopeDefinition(clazz.name) {
    val Scope.application: T
        get() = parameters[0]
}