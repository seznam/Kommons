package cz.seznam.di.scope

import android.support.v7.app.AppCompatActivity

/**
 * @author Jakub Janda
 */
class ActivityScopeDefinition<T : AppCompatActivity>(clazz: Class<T>) : ScopeDefinition(clazz.name) {
    val Scope.activity: T
        get() = parameters[0]
}