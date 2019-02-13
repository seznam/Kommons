package cz.seznam.di

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import cz.seznam.di.scope.*
import cz.seznam.di.tree.ScopeTreeBuilder

/**
 * @author Jakub Janda
 */
object Kodi {
    private val scopeDefinitions: MutableMap<String, ScopeDefinition> = mutableMapOf()

    internal val scopes: MutableMap<String, Scope> = mutableMapOf()

    fun registerScopeDefinition(scope: ScopeDefinition) {
        scopeDefinitions[scope.name] = scope
    }

    fun removeScopeDefinition(scope: ScopeDefinition) {
        scopeDefinitions.remove(scope.name)
    }

    fun findScopeDefinition(name: String): ScopeDefinition? {
        return scopeDefinitions[name]
    }

    fun createScope(
        name: String,
        parent: Scope? = null,
        params: ScopeParameters = ScopeParameters()
    ): Scope {
        val definition = scopeDefinitions[name] ?: throw RuntimeException("There is no definition for scope $name")
        return Scope(definition, parent, params)
    }

    fun createScope(
        definition: ScopeDefinition,
        parent: Scope? = null,
        params: ScopeParameters = ScopeParameters()
    ): Scope {
        return Scope(definition, parent, params)
    }

    fun start(app: Application, scopeDefinitions: List<ScopeDefinition>) {
        scopeDefinitions.forEach { registerScopeDefinition(it) }
        ScopeTreeBuilder(app)
    }
}

fun scope(name: String = "", registrator: ScopeDefinition.() -> Unit): ScopeDefinition {
    return ScopeDefinition(name).apply { registrator.invoke(this) }
}

inline fun <reified T : Application> applicationScope(registrator: ApplicationScopeDefinition<T>.() -> Unit): ScopeDefinition {
    return ApplicationScopeDefinition(T::class.java).apply { registrator.invoke(this) }
}

inline fun <reified T : AppCompatActivity> activityScope(registrator: ActivityScopeDefinition<T>.() -> Unit): ScopeDefinition {
    return ActivityScopeDefinition(T::class.java).apply { registrator.invoke(this) }
}

inline fun <reified T : androidx.fragment.app.Fragment> fragmentScope(registrator: FragmentScopeDefinition<T>.() -> Unit): ScopeDefinition {
    return FragmentScopeDefinition(T::class.java).apply { registrator.invoke(this) }
}

fun Application.startKodi(vararg scopeDefinitions: ScopeDefinition) = Kodi.start(this, scopeDefinitions.asList())

val Application.scope: Scope?
    get() = Kodi.scopes[hashCode().toString()]

val AppCompatActivity.scope: Scope?
    get() = Kodi.scopes[hashCode().toString()]

val androidx.fragment.app.Fragment.scope: Scope?
    get() = Kodi.scopes[hashCode().toString()]

inline fun <reified T> Application.obtain(): T {
    return scope?.obtain<T>() ?: throw RuntimeException("There is no dependency scope for ${this.javaClass.name}")
}


inline fun <reified T> AppCompatActivity.obtain(): T {
    return scope?.obtain<T>() ?: throw RuntimeException("There is no dependency scope for ${this.javaClass.name}")
}

inline fun <reified T> androidx.fragment.app.Fragment.obtain(): T {
    return scope?.obtain<T>() ?: throw RuntimeException("There is no dependency scope for ${this.javaClass.name}")
}

inline fun <reified T> Application.lazyObtain(): Lazy<T> = lazy { obtain<T>() }

inline fun <reified T> AppCompatActivity.lazyObtain(): Lazy<T> = lazy { obtain<T>() }

inline fun <reified T> androidx.fragment.app.Fragment.lazyObtain(): Lazy<T> = lazy { obtain<T>() }

