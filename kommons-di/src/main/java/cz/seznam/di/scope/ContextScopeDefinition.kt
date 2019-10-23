package cz.seznam.di.scope

import android.content.Context

/**
 * @author Jakub Janda
 */
class ContextScopeDefinition(name: String) : ScopeDefinition(name) {
  val Scope.context: Context
    get() = parameters[0]
}