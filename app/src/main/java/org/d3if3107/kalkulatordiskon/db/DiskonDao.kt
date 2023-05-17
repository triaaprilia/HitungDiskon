package org.d3if3107.kalkulatordiskon.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DiskonDao {
    @Insert
    fun insert(kalkulatordiskon: DiskonEntity)

    @Query("SELECT * FROM kalkulatordiskon ORDER BY id DESC")
    fun getLastDiskon(): LiveData<List<DiskonEntity>>

    @Query("DELETE FROM kalkulatordiskon")
    fun clearData()
}