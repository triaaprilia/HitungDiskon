package org.d3if3107.kalkulatordiskon

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if3107.kalkulatordiskon.databinding.FragmentMainBinding
import org.d3if3107.kalkulatordiskon.db.DiskonDb
import org.d3if3107.kalkulatordiskon.model.HasilDiskon
import org.d3if3107.kalkulatordiskon.ui.histori.HistoriViewModel
import org.d3if3107.kalkulatordiskon.ui.histori.HistoriViewModelFactory

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
            val db = DiskonDb.getInstance(requireContext())
            val factory = MainViewModelFactory(db.dao)
            ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener {kalkulatorDiskon()}
        viewModel.getHasilDiskon().observe(requireActivity()) { showResult(it) }
        binding.shareButton.setOnClickListener { shareData() }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_mainFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(R.id.action_mainFragment_to_aboutFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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

    }

    private fun shareData() {
        val message = getString(R.string.bagikan_template,
            binding.hargaInp.text.toString(),
            binding.diskonInp.text.toString(),
            binding.hasil.text.toString(),
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }


    private fun showResult(result: HasilDiskon?) {
        if (result == null) return
        binding.hasil.text = getString(R.string.hasil_x, result.jumlahdiskon)
        binding.buttonGroup.visibility = View.VISIBLE
    }
}