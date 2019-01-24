package cz.seznam.kommons.rx

import io.reactivex.Scheduler


/**
 * @author Jakub Janda
 */
interface IRxSchedulers {
    fun io(): Scheduler

    fun mainThread(): Scheduler

    fun newThread(): Scheduler

    fun computation(): Scheduler
}
