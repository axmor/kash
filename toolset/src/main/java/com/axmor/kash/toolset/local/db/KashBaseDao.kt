package com.axmor.kash.toolset.local.db

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert


/**
 * Created by akolodyazhnyy on 8/25/2017.
 */

/**
 * Room base dao for certain Entity.
 * U should describe ur own extended by this.
 */

//@Dao
interface KashBaseDao<Entity> {

    //@Query("SELECT * FROM entity_table")
    //fun getAll(): Flowable<List<Entity>>

    //@Query("SELECT * from entity_table where id = :arg0 LIMIT 1")
    //fun loadEntityById(id: Int): Flowable<Entity>

    @Insert
    fun insertAll(vararg entities: Entity)

    @Delete
    fun delete(entity: Entity)
}