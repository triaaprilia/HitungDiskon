package org.d3if3107.kalkulatordiskon.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProdukDiskon(
    var namaProduk: String? = "",
    var diskon: String? = "",
    var imageId: String
) : Parcelable
