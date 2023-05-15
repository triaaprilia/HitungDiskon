package org.d3if3107.kalkulatordiskon.model

import org.d3if3107.kalkulatordiskon.db.DiskonEntity

fun DiskonEntity.hitungDiskon(): HasilDiskon {
    val jumlahDiskon = harga * diskon / 100
    val hasilDiskon = harga - jumlahDiskon

    return HasilDiskon(hasilDiskon)
}
