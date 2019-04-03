package cz.seznam.di.scope

/**
 * @author Jakub Janda
 */
class FragmentScopeDefinition<T : androidx.fragment.app.Fragment>(clazz: Class<T>) : ScopeDefinition(clazz.name) {
    val Scope.fragment: T
        get() = parameters[0]
}