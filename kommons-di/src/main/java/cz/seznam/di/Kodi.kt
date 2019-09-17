package cz.seznam.di

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cz.seznam.di.scope.*
import cz.seznam.di.tree.ApplicationScopeTreeBuilder

/**
 * @author Jakub Janda
 */
object Kodi {
  private val scopeDefinitions: MutableMap<String, MutableList<ScopeDefinition>> = mutableMapOf()

  internal val scopes: MutableMap<String, Scope> = mutableMapOf()

  fun registerScopeDefinition(scope: ScopeDefinition) {
    val definitions = scopeDefinitions[scope.name]
    if (definitions != null) {
      definitions += scope
    } else {
      scopeDefinitions[scope.name] = mutableListOf(scope)
    }
  }

  fun removeScopeDefinition(scope: ScopeDefinition) {
    scopeDefinitions.remove(scope.name)
  }

  fun findScopeDefinition(name: String): List<ScopeDefinition>? {
    return scopeDefinitions[name]
  }

  fun createScope(
    name: String,
    parent: Scope? = null,
    params: ScopeParameters = ScopeParameters()
  ): Scope {
    val definition =
      scopeDefinitions[name] ?: throw RuntimeException("There is no definition for scope $name")
    return Scope(definition, parent, params)
  }

  fun createScope(
    definition: List<ScopeDefinition>,
    parent: Scope? = null,
    params: ScopeParameters = ScopeParameters()
  ): Scope {
    return Scope(definition, parent, params)
  }

  fun createContextScope(context: Context, scopeDefinition:  List<ContextScopeDefinition>): Scope {
    return createScope(scopeDefinition, null, ScopeParameters(context))
  }

  fun start(
    app: Application,
    appScope: Scope?,
    scopeDefinitions: List<ScopeDefinition>
  ) {
    scopeDefinitions.forEach { registerScopeDefinition(it) }
    ApplicationScopeTreeBuilder(app, appScope)
  }
}

fun scope(name: String = "", registrator: ScopeDefinition.() -> Unit): ScopeDefinition {
  return ScopeDefinition(name).apply { registrator.invoke(this) }
}

inline fun <reified T : AppCompatActivity> activityScope(registrator: ActivityScopeDefinition<T>.() -> Unit): ActivityScopeDefinition<T> {
  return ActivityScopeDefinition(T::class.java).apply { registrator.invoke(this) }
}

inline fun <reified T : Fragment> fragmentScope(registrator: FragmentScopeDefinition<T>.() -> Unit): FragmentScopeDefinition<T> {
  return FragmentScopeDefinition(T::class.java).apply { registrator.invoke(this) }
}

inline fun contextScope(
  name: String,
  registrator: ContextScopeDefinition.() -> Unit
): ContextScopeDefinition {
  return ContextScopeDefinition(name).apply { registrator.invoke(this) }
}

fun Application.startKodi(vararg scopeDefinitions: ScopeDefinition) =
  Kodi.start(this, null, scopeDefinitions.asList())

val AppCompatActivity.scope: Scope?
  get() = Kodi.scopes[hashCode().toString()]

val Fragment.scope: Scope?
  get() = Kodi.scopes[hashCode().toString()]

inline fun <reified T> AppCompatActivity.obtain(): T? {
  return scope?.obtain<T>()
}

inline fun <reified T> Fragment.obtain(): T? {
  return scope?.obtain<T>()
}

inline fun <reified T> AppCompatActivity.lazyObtain(): Lazy<T> = lazy {
  scope?.obtain<T>()
    ?: throw RuntimeException("There is no dependency scope for ${this.javaClass.name}")
}

inline fun <reified T> Fragment.lazyObtain(): Lazy<T> = lazy {
  scope?.obtain<T>()
    ?: throw RuntimeException("There is no dependency scope for ${this.javaClass.name}")
}

