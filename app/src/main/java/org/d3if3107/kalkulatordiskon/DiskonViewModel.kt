package org.d3if3107.kalkulatordiskon

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3107.kalkulatordiskon.db.DiskonDao
import org.d3if3107.kalkulatordiskon.db.DiskonEntity
import org.d3if3107.kalkulatordiskon.model.HasilDiskon
import org.d3if3107.kalkulatordiskon.model.ProdukDiskon
import org.d3if3107.kalkulatordiskon.model.hitungDiskon
import org.d3if3107.kalkulatordiskon.network.KalkulatorDiskon
import org.d3if3107.kalkulatordiskon.network.UpdateWorker
import java.util.concurrent.TimeUnit

class DiskonViewModel(private val db: DiskonDao) : ViewModel() {
    private val listProdukDiskon = MutableLiveData<List<ProdukDiskon>>()
    private val hasilDiskon = MutableLiveData<HasilDiskon>()
    private val status = MutableLiveData<KalkulatorDiskon.ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(KalkulatorDiskon.ApiStatus.LOADING)
            try {
                listProdukDiskon.postValue(KalkulatorDiskon.service.getProduk())
                status.postValue(KalkulatorDiskon.ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(KalkulatorDiskon.ApiStatus.FAILED)
            }
        }
    }

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
    fun getStatus(): LiveData<KalkulatorDiskon.ApiStatus> = status
    fun getListProdukDiskon(): LiveData<List<ProdukDiskon>> = listProdukDiskon

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}