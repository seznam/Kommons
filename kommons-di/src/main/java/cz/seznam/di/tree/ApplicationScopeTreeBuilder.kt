package cz.seznam.di.tree

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import cz.seznam.di.Kodi
import cz.seznam.di.scope.Scope
import cz.seznam.di.scope.ScopeParameters

/**
 * @author Jakub Janda
 */
class ApplicationScopeTreeBuilder(app: Application, private val appScope: Scope?) :
  Application.ActivityLifecycleCallbacks,
  FragmentManager.FragmentLifecycleCallbacks() {

  init {
    app.registerActivityLifecycleCallbacks(this)
  }

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    if (activity is AppCompatActivity) {
      activity.supportFragmentManager.registerFragmentLifecycleCallbacks(this, true)

      val scopeDefinition = Kodi.findScopeDefinition(activity::class.java.name)

      if (scopeDefinition != null) {
        val scope = Kodi.createScope(scopeDefinition, appScope, ScopeParameters(activity))
        Kodi.scopes[activity.hashCode().toString()] = scope
      } else {
        appScope?.apply { Kodi.scopes[activity.hashCode().toString()] = this }
      }
    }
  }

  override fun onActivityDestroyed(activity: Activity) {
    if (activity is AppCompatActivity) {
      activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(this)
      Kodi.scopes.remove(activity.hashCode().toString())
    }
  }

  override fun onFragmentPreCreated(
    fm: FragmentManager,
    f: Fragment,
    savedInstanceState: Bundle?
  ) {
    super.onFragmentCreated(fm, f, savedInstanceState)

    val scopeDefinition = Kodi.findScopeDefinition(f::class.java.name)

    if (scopeDefinition != null) {
      val scope = Kodi.createScope(scopeDefinition, findParentScope(f), ScopeParameters(f))
      Kodi.scopes[f.hashCode().toString()] = scope
    } else {
      val scope = findParentScope(f)
      if (scope != null) {
        Kodi.scopes[f.hashCode().toString()] = scope
      }
    }
  }

  override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
    super.onFragmentDestroyed(fm, f)
    Kodi.scopes.remove(f.hashCode().toString())
  }

  private fun findParentScope(fragment: Fragment): Scope? {
    val parent = fragment.parentFragment
    val activity = fragment.activity

    if (parent != null) {
      return Kodi.scopes[parent.hashCode().toString()] ?: findParentScope(parent)
    } else if (activity is AppCompatActivity) {
      return Kodi.scopes[activity.hashCode().toString()] ?: appScope
    }

    return null
  }

  override fun onActivityPaused(activity: Activity) = Unit

  override fun onActivityResumed(activity: Activity) = Unit

  override fun onActivityStarted(activity: Activity) = Unit

  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

  override fun onActivityStopped(activity: Activity) = Unit
}