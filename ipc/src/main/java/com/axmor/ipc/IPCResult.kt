package com.axmor.ipc

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
class IPCResult<T> private constructor(var value: T?, val errorMessage: String?) : Parcelable {

    val isSuccessful: Boolean
        get() = errorMessage == null

    override fun writeToParcel(dest: Parcel, flags: Int) {
        if (isSuccessful) {
            dest.writeInt(1)
            dest.writeValue(value)
        } else {
            dest.writeInt(0)
            dest.writeString(errorMessage)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        fun <T> success( value: T): IPCResult<T> {
            return IPCResult(value, null)
        }

        fun <T> error(errorMessage: String?): IPCResult<T> {
            if (errorMessage == null) {
                throw NullPointerException()
            }
            return IPCResult<T>(null, errorMessage)
        }

        val CREATOR: Creator<IPCResult<*>> = object : Creator<IPCResult<*>> {
            override fun createFromParcel(`in`: Parcel): IPCResult<*> {
                val successful = `in`.readInt() == 1
                if (successful) {
                    val value = `in`.readValue(this.javaClass.classLoader)
                    return success(value)
                } else {
                    val errorMessage = `in`.readString()
                    return error<String>(errorMessage)
                }
            }

            override fun newArray(size: Int): Array<IPCResult<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
