package com.example.recordkeeper.running

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.recordkeeper.R
import com.example.recordkeeper.databinding.ActivityEditRunningRecordBinding

class EditRunningRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRunningRecordBinding
    private var distance: String? = null
    private lateinit var runningRecordsPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityEditRunningRecordBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        applyWindowInsets()
        initializeLateInits()
        displayRecord()
        binding.buttonSave.setOnClickListener {
            saveRecord()
            finish()
        }


        //default shared preferences
//        val applicationPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//        applicationPreferences.edit{
//            putString("distance", intent.getStringExtra("distance"))
//        }

        //shared preferences private to this activity
//        val activityPreferences = getPreferences(Context.MODE_PRIVATE)
//        activityPreferences.edit{
//            putString("distance", "10KM UPDATE")
//        }

        //preferences with a custom name
//        val fileNamePreferences =   getSharedPreferences("file_name", Context.MODE_PRIVATE)
//        fileNamePreferences.edit{
//            putBoolean("boolean", true)
//        }
    }

    private fun initializeLateInits() {
        intent.getStringExtra("distance")?.let {
            title = it
        }
        distance = intent.getStringExtra("distance")
        runningRecordsPreferences = getSharedPreferences("running_records", Context.MODE_PRIVATE)
    }

    private fun displayRecord() {
        binding.editTextRecord.setText(
            runningRecordsPreferences.getString("$distance record", null)
        )
        binding.editTextDate.setText(
            runningRecordsPreferences.getString("$distance date", null)
        )
    }

    private fun saveRecord() {
        val runningRecordsPreferences =   getSharedPreferences("running_records", Context.MODE_PRIVATE)
        if (distance == null) {
            return
        }
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()
        runningRecordsPreferences.edit {
            putString("$distance record", record)
            putString("$distance date", date)
        }
    }

    private fun applyWindowInsets(){
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val paddingOriginal = 24 // función para convertir dp a px
            v.setPadding(
                paddingOriginal + systemBars.left,
                paddingOriginal + systemBars.top,
                paddingOriginal + systemBars.right,
                paddingOriginal + systemBars.bottom
            )
            insets
        }
    }
}