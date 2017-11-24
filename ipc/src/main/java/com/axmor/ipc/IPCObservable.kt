package com.axmor.ipc

/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
interface IPCObservable<out T> {
    fun addObserver(observer: IPCObserver<T>)
    fun removeObserver(observer: IPCObserver<T>)
}
