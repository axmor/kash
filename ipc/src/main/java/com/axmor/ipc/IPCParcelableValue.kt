package com.axmor.ipc

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
class IPCParcelableValue<out T>(val value: T) : Parcelable {

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeValue(value)
    }

    override fun describeContents() = 0

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<IPCParcelableValue<*>> = object : Parcelable.Creator<IPCParcelableValue<*>> {
            override fun createFromParcel(source: Parcel): IPCParcelableValue<*> = IPCParcelableValue<Any>(source.readValue(IPCParcelableValue::class.java.classLoader))
            override fun newArray(size: Int): Array<IPCParcelableValue<*>?> = arrayOfNulls(size)
        }
    }
}
