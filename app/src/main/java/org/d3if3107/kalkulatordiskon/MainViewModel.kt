package org.d3if3107.kalkulatordiskon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3107.kalkulatordiskon.db.DiskonEntity
import org.d3if3107.kalkulatordiskon.model.HasilDiskon
import org.d3if3107.kalkulatordiskon.model.hitungDiskon

class MainViewModel : ViewModel() {

    private val hasilDiskon = MutableLiveData<HasilDiskon?>()

    fun hitungDiskon(harga: Double, diskon: Double) {
        val dataBmi = DiskonEntity(
            harga = harga,
            diskon = diskon
        )
        hasilDiskon.value = dataBmi.hitungDiskon()
    }

    fun getHasilDiskon(): LiveData<HasilDiskon?> = hasilDiskon
}