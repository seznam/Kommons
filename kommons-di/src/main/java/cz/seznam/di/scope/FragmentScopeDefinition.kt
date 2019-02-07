package cz.seznam.di.scope

import android.support.v4.app.Fragment

/**
 * @author Jakub Janda
 */
class FragmentScopeDefinition<T : Fragment>(clazz: Class<T>) : ScopeDefinition(clazz.name) {
    val Scope.fragment: T
        get() = parameters[0]
}