package com.example.myapplication.daggerHiltRunningApp.ui.fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.daggerHiltRunningApp.adapters.RunAdapter
import com.example.myapplication.daggerHiltRunningApp.other.Constants
import com.example.myapplication.daggerHiltRunningApp.other.SortType
import com.example.myapplication.daggerHiltRunningApp.other.TrackingUtility
import com.example.myapplication.daggerHiltRunningApp.ui.RunningAppMainActivity
import com.example.myapplication.daggerHiltRunningApp.ui.viewmodels.MainViewModel
import com.example.myapplication.databinding.FragmentRunBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RunFragment: Fragment(R.layout.fragment_run) {

    private lateinit var binding: FragmentRunBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var runAdapter: RunAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRunBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        getBatteryPermission()
        setupRecyclerView()

        when(viewModel.sortType)    {
            SortType.DATE -> binding.spFilter.setSelection(0)
            SortType.RUNNING_TIME -> binding.spFilter.setSelection(1)
            SortType.DISTANCE -> binding.spFilter.setSelection(2)
            SortType.AVG_SPEED -> binding.spFilter.setSelection(3)
            SortType.CALORIES_BURNED -> binding.spFilter.setSelection(4)

        }

        binding.spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position)   {
                    0 -> viewModel.sorRuns(SortType.DATE)
                    1 -> viewModel.sorRuns(SortType.RUNNING_TIME)
                    2 -> viewModel.sorRuns(SortType.DISTANCE)
                    3 -> viewModel.sorRuns(SortType.AVG_SPEED)
                    4 -> viewModel.sorRuns(SortType.CALORIES_BURNED)

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        viewModel.runs.observe(viewLifecycleOwner)  {
            runAdapter.submitList(it)
        }
        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }


    private fun setupRecyclerView() = binding.rvRuns.apply {
        runAdapter = RunAdapter()
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getBatteryPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)  {
            val intent = Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            val uri = Uri.fromParts("package", (activity as RunningAppMainActivity).packageName, null)
            intent.data = uri
            startActivity(intent)
        }
    }



    // Permissions Code
//    private fun hasLocationFinePermission() =
//        ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//    private fun hasLocationForegroundPermission() =
//        ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//    private fun hasLocationBackgroundPermission() =
//        ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    private fun requestPermission() {
        if(!TrackingUtility.hasBatteryOptimizationPermission(this.requireContext())) {
            Log.d("BATTERY_PERMISSION", "Not Granted")
            getBatteryPermission()
        }   else    {
            Log.d("BATTERY_PERMISSION", "Already Granted")
        }

        if(TrackingUtility.hasLocationPermissions(this.requireContext()) >= 0){  return  }
        val permissions = mutableListOf<String>()
            if (!TrackingUtility.hasLocationFinePermission(this.requireContext())) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (!TrackingUtility.hasLocationForegroundPermission(this.requireContext())) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            if (TrackingUtility.hasLocationForegroundPermission(this.requireContext()) && !TrackingUtility.hasLocationBackgroundPermission(this.requireContext())) {
                permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
        if(permissions.isNotEmpty()){
            ActivityCompat.requestPermissions(this.requireActivity(), permissions.toTypedArray(),Constants.REQUEST_CODE_LOCATION_PERMISSION)
        }
    }
}