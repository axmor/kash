package com.axmor.kash.toolset.local.db

import android.arch.persistence.room.RoomDatabase

/**
 * Created by akolodyazhnyy on 8/25/2017.
 */

/**
 * Database class contains methods to get dao by his class.
 * U should extend ur own database class by this.
 */

//@Database(entities = arrayOf(Entity::class), version = 1)
abstract class KashDatabase : RoomDatabase() {
    //abstract fun entityDao(): EntityDao

    /**
     * get Dao by Dao interface name
     */
    fun <Dao> getDaoReference(dClass: Class<Dao>): Dao {
        for (m in this::class.java.declaredMethods) {
            if (m.returnType == dClass) {
                return m.invoke(this) as Dao
            }
        }
        throw RuntimeException("Can't find method to return ${dClass.canonicalName} dao")
    }
}