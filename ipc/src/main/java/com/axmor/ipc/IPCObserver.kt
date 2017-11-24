package com.axmor.ipc

/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
interface IPCObserver<in T> {
    fun onChanged(value: T)
}
