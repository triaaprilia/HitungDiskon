package org.d3if3107.kalkulatordiskon.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if3107.kalkulatordiskon.DiskonViewModel
import org.d3if3107.kalkulatordiskon.MainActivity
import org.d3if3107.kalkulatordiskon.MainViewModelFactory
import org.d3if3107.kalkulatordiskon.databinding.FragmentDiskonBinding
import org.d3if3107.kalkulatordiskon.db.DiskonDb
import org.d3if3107.kalkulatordiskon.network.KalkulatorDiskon

class DiskonFragment : Fragment() {
    private lateinit var binding: FragmentDiskonBinding

    private val viewModel: DiskonViewModel by lazy {
        val db = DiskonDb.getInstance(requireContext())
        val factory = MainViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[DiskonViewModel::class.java]
    }

    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiskonBinding.inflate(layoutInflater, container, false)
        mainAdapter = MainAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.title = "Diskon Produk"

        with(viewModel) {
           getListProdukDiskon().observe(viewLifecycleOwner) {
                mainAdapter.updateData(it)

                with(binding.recyclerView) {
                    adapter = mainAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    setHasFixedSize(true)
                }
            }
            getStatus().observe(viewLifecycleOwner) { updateProgress(it) }
           scheduleUpdater(requireActivity().application)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun updateProgress(status: KalkulatorDiskon.ApiStatus) {
        when (status) {
            KalkulatorDiskon.ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            KalkulatorDiskon.ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermission()
                }
            }
            KalkulatorDiskon.ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }

}