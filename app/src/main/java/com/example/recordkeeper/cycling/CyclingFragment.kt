package com.example.recordkeeper.cycling

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recordkeeper.databinding.FragmentCyclingBinding

class CyclingFragment: Fragment() {

    private lateinit var binding: FragmentCyclingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCyclingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.containerLongestRide.setOnClickListener{launchingEditCyclingRecordActivity("Longest Ride")}
        binding.containerBiggestClimb.setOnClickListener{launchingEditCyclingRecordActivity("Biggest Climb")}
        binding.containerBestSpeed.setOnClickListener{ launchingEditCyclingRecordActivity("Best Speed")}
    }

    private fun launchingEditCyclingRecordActivity(travelType: String) {
        val intent = Intent(context, EditCyclingRecordActivity::class.java)
        intent.putExtra("travelType", travelType)
        startActivity(intent)
    }
}