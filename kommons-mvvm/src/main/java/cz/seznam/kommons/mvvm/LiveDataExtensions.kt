package cz.seznam.kommons.mvvm

import androidx.lifecycle.*
import kotlin.reflect.KMutableProperty0

/** Extension passing method reference as LiveData observer.
 *
 * @param owner lifecycle owner
 * @param callback called when value is changed
 *
 * @author Jakub Janda
 */
fun <T : Any?> LiveData<T>.observe(
  owner: LifecycleOwner,
  callback: (value: T?) -> Unit
) = observe(owner, androidx.lifecycle.Observer { callback.invoke(it) })


class LiveDataObservers(val lifecycleOwner: LifecycleOwner) {
  fun <T> LiveData<T>.observe(callback: (value: T?) -> Unit) = observe(lifecycleOwner, Observer {
    callback.invoke(it)
  })

  infix fun <T> LiveData<T>.by(setter: (v: T?) -> Unit) {
    this.observe(setter)
  }

  infix fun <T> LiveData<T>.by(property: KMutableProperty0<T?>) {
    this.observe { property.set(it) }
  }
}

fun LifecycleOwner.observe(init: LiveDataObservers.() -> Unit): LiveDataObservers {
  val views = LiveDataObservers(this)  // create the receiver object
  views.init()        // pass the receiver object to the lambda
  return views
}

fun <X, Y> LiveData<X>.map(mapper: (X?) -> Y?): LiveData<Y> = Transformations.map(this, mapper)

/** One shot livedata.
 *
 * Sets value to null immediately after the actual value.
 *
 * @deprecated Use some other mechanism.
 */
fun <T> MutableLiveData<T>.shoot(value: T) {
  setValue(value)
  setValue(null)
}


fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, setter: (v: T) -> Unit) {
  this.observe(lifecycleOwner) {
    if (it != null) {
      setter(it)
    }
  }
}

/** Observer forever and return observer you can use lately to stop listen the LiveData.
 *
 */
fun <T> LiveData<T>.observe(setter: (v: T?) -> Unit): Observer<T> {
  val observer = Observer<T> {
    setter(it)
  }

  observeForever(observer)
  return observer
}
