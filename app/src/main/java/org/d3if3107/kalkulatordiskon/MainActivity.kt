package org.d3if3107.kalkulatordiskon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import org.d3if3107.kalkulatordiskon.databinding.ActivityMainBinding
import java.text.DecimalFormat
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {kalkulatorDiskon()}
        }
    private fun kalkulatorDiskon() {
        val harga = binding.hargaInp.text.toString()
        if (TextUtils.isEmpty(harga)) {
            Toast.makeText(this, R.string.harga_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val diskon = binding.diskonInp.text.toString()
        if (TextUtils.isEmpty(diskon)) {
            Toast.makeText(this, R.string.diskon_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val jumlahDiskon=  harga.toDouble() * diskon.toDouble() / 100
        val hasilDiskon = harga.toDouble() - jumlahDiskon
        binding.hasil.text = getString(R.string.hasil_x,hasilDiskon)
    }
}