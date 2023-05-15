package org.d3if3107.kalkulatordiskon

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.d3if3107.kalkulatordiskon.databinding.FragmentMainBinding
import org.d3if3107.kalkulatordiskon.model.HasilDiskon

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener {kalkulatorDiskon()}
        viewModel.getHasilDiskon().observe(requireActivity()) { showResult(it) }
    }

    private fun kalkulatorDiskon() {
        val harga = binding.hargaInp.text.toString()
        if (TextUtils.isEmpty(harga)) {
            Toast.makeText(context, R.string.harga_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val diskon = binding.diskonInp.text.toString()
        if (TextUtils.isEmpty(diskon)) {
            Toast.makeText(context, R.string.diskon_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.hitungDiskon(
            harga.toDouble(),
            diskon.toDouble())

//        val jumlahDiskon=  harga.toDouble() * diskon.toDouble() / 100
//        val hasilDiskon = harga.toDouble() - jumlahDiskon
//        binding.hasil.text = getString(R.string.hasil_x,hasilDiskon)
    }

    private fun showResult(result: HasilDiskon?) {
        if (result == null) return
        binding.hasil.text = getString(R.string.hasil_x, result.jumlahdiskon)
    }
}