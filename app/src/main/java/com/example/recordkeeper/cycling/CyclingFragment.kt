package com.example.recordkeeper.cycling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recordkeeper.databinding.FragmentCyclingBinding
import com.example.recordkeeper.edit_record.EditRecordActivity
import com.example.recordkeeper.edit_record.EditRecordScreenData

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

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun displayRecords() {
        val cyclingPreferences = requireContext().getSharedPreferences("cycling_records", Context.MODE_PRIVATE)
        binding.textViewLongestRideValue.text = cyclingPreferences.getString("Longest Ride record", null)
        binding.textViewLongestRideDate.text = cyclingPreferences.getString("Longest Ride date", null)
        binding.textViewBiggestClimbValue.text = cyclingPreferences.getString("Biggest Climb record", null)
        binding.textViewBiggestClimbDate.text = cyclingPreferences.getString("Biggest Climb date", null)
        binding.textViewBestSpeedValue.text = cyclingPreferences.getString("Best Speed record", null)
        binding.textViewBestSpeedDate.text = cyclingPreferences.getString("Best Speed date", null)
    }

    private fun setOnClickListeners() {
        binding.containerLongestRide.setOnClickListener{launchingEditCyclingRecordActivity("Longest Ride","Distance")}
        binding.containerBiggestClimb.setOnClickListener{launchingEditCyclingRecordActivity("Biggest Climb","Height")}
        binding.containerBestSpeed.setOnClickListener{ launchingEditCyclingRecordActivity("Best Speed","Average Speed")}
    }

    private fun launchingEditCyclingRecordActivity(record: String, recordFieldHint:String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra("edit_record_screen_Data", EditRecordScreenData(
            record = record,
            sharedPreferencesName = "cycling_records",
            recordFieldHint = recordFieldHint
        ))
        startActivity(intent)
    }
}