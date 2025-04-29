package com.example.recordkeeper.running

import android.content.Context
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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityEditRunningRecordBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_running_record)
        applyWindowInsets()

        intent.getStringExtra("distance")?.let {
            title = it
        }

        println(intent.getStringExtra("distance"))

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
    private fun applyWindowInsets(){
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}