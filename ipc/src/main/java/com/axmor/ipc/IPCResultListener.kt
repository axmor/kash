package com.axmor.ipc

/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
interface IPCResultListener<T> {
    fun onResult(result: IPCResult<T>)
}
