package org.d3if3107.kalkulatordiskon.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3107.kalkulatordiskon.model.ProdukDiskon
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL =
    "https://raw.githubusercontent.com/triaaprilia/kalkulatordiskon-api/static-api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface KalkulatorDiskonApiService {
    @GET("produk.json")
    suspend fun getProduk(): List<ProdukDiskon>
}

object KalkulatorDiskon {
    enum class ApiStatus { LOADING, SUCCESS, FAILED }

    val service: KalkulatorDiskonApiService by lazy {
        retrofit.create(KalkulatorDiskonApiService::class.java)
    }

    fun getImageUrl(imageId: String): String {
        return "$BASE_URL$imageId.jpg"
    }
}