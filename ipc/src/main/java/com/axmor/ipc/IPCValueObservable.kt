package com.axmor.ipc

import android.util.Log

/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
class IPCValueObservable<T>(private var value: T) : IPCObservable<T> {
    private val observers = HashSet<IPCObserver<T>>()

    @Synchronized
    fun getValue(): T {
        return value
    }

    @Synchronized
    fun setValue(value: T) {
        this.value = value
        for (observer in observers) {
            observer.onChanged(value)
        }
    }

    @Synchronized
    override fun addObserver(observer: IPCObserver<T>) {
        if (observers.contains(observer)) {
            Log.e(TAG, "addObserver: Observer already registered")
            return
        }

        observers.add(observer)
        observer.onChanged(value)
    }

    @Synchronized
    override fun removeObserver(observer: IPCObserver<T>) {
        val removed = observers.remove(observer)
        if (!removed) {
            Log.e(TAG, "removeObserver: Observer wasn't registered")
        }
    }

    companion object {
        val TAG = "ValueObservable"
    }
}
