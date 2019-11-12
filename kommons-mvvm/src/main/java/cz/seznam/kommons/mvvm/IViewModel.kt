package cz.seznam.kommons.mvvm

/** Base interface for implementing models by MVVM pattern.
 *
 * @author Jakub Janda
 */
interface IViewModel {
    /** Called when the viewmodel bind view.
     *
     */
    fun onBind() {}

    /** Called when the viewmodel unbind view.
     *
     */
    fun onUnbind() {}
}
