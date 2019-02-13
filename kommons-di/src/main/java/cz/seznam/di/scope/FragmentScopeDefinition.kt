package cz.seznam.di.scope

import androidx.fragment.app.Fragment

/**
 * @author Jakub Janda
 */
class FragmentScopeDefinition<T : androidx.fragment.app.Fragment>(clazz: Class<T>) : ScopeDefinition(clazz.name) {
    val Scope.fragment: T
        get() = parameters[0]
}