package com.example.recordkeeper.running

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recordkeeper.databinding.FragmentRunningBinding
import com.example.recordkeeper.edit_record.EditRecordActivity
import com.example.recordkeeper.edit_record.EditRecordScreenData

class RunningFragment: Fragment() {

    private lateinit var binding: FragmentRunningBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentRunningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun setUpClickListeners() {
        binding.container5km.setOnClickListener{launchRunningRecordActivity("5km")}
        binding.container10km.setOnClickListener{launchRunningRecordActivity("10km")}
        binding.container121km.setOnClickListener{launchRunningRecordActivity("12km")}
    }

    private fun displayRecords() {
        val runningPreferences = requireContext().getSharedPreferences("running_records", Context.MODE_PRIVATE)
        binding.textView5kmValue.text = runningPreferences.getString("5km record", null)
        binding.textView5kmDate.text = runningPreferences.getString("5km date", null)
        binding.textView10kmValue.text = runningPreferences.getString("10km record", null)
        binding.textView10kmDate.text = runningPreferences.getString("10km date", null)
        binding.textView12kmValue.text = runningPreferences.getString("12km record", null)
        binding.textView12kmDate.text = runningPreferences.getString("12km date", null)
    }

    private fun launchRunningRecordActivity(distance: String) {
        //in fragments, we cant use this as context, so we use requireContext()
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra("edit_record_screen_Data", EditRecordScreenData(
            record = distance,
            sharedPreferencesName = "running_records",
            recordFieldHint = "Time"
        ))
        intent.putExtra("distance", distance)
        startActivity(intent)
    }

}