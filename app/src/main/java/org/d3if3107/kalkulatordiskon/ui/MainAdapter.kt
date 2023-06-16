package org.d3if3107.kalkulatordiskon.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3107.kalkulatordiskon.R
import org.d3if3107.kalkulatordiskon.databinding.ListDiskonBinding
import org.d3if3107.kalkulatordiskon.model.ProdukDiskon
import org.d3if3107.kalkulatordiskon.network.KalkulatorDiskon

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private val data = mutableListOf<ProdukDiskon>()
    fun updateData(newData: List<ProdukDiskon>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ListDiskonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(produkDiskon: ProdukDiskon) = with(binding) {
            namaTextView.text = produkDiskon.namaProduk
            latinTextView.text = produkDiskon.diskon
            Glide.with(imageView.context)
                .load(KalkulatorDiskon.getImageUrl(produkDiskon.imageId))
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)


            root.setOnClickListener {
                Toast.makeText(
                    root.context,
                    "${produkDiskon.diskon} ditap!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListDiskonBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}