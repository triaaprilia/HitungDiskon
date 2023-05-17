package org.d3if3107.kalkulatordiskon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3107.kalkulatordiskon.db.DiskonDao
import org.d3if3107.kalkulatordiskon.db.DiskonEntity
import org.d3if3107.kalkulatordiskon.model.HasilDiskon
import org.d3if3107.kalkulatordiskon.model.hitungDiskon

class MainViewModel(private val db: DiskonDao) : ViewModel() {

    private val hasilDiskon = MutableLiveData<HasilDiskon?>()

    fun hitungDiskon(harga: Double, diskon: Double) {
        val dataBmi = DiskonEntity(
            harga = harga,
            diskon = diskon
        )
        hasilDiskon.value = dataBmi.hitungDiskon()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataBmi)
            }
        }
    }

    fun getHasilDiskon(): LiveData<HasilDiskon?> = hasilDiskon
}