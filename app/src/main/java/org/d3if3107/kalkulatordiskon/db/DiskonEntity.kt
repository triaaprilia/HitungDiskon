package org.d3if3107.kalkulatordiskon.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diskon")
data class DiskonEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var harga: Double,
    var diskon: Double
)
