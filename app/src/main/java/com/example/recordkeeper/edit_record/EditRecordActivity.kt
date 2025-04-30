package com.example.recordkeeper.edit_record

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recordkeeper.databinding.ActivityEditRecordBinding

class EditRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRecordBinding
    public val screenData: EditRecordScreenData by lazy{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("edit_record_screen_Data",EditRecordScreenData::class.java) as EditRecordScreenData
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("edit_record_screen_Data") as EditRecordScreenData
        }
    }
    private val recordPreferences: SharedPreferences by lazy {
        getSharedPreferences(screenData.sharedPreferencesName, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityEditRecordBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        applyWindowInsets()
        displayRecord()
        setUpUi()


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

    private fun setUpUi() {

        title = screenData.record + " Record"

        binding.textInputRecord.hint = screenData.recordFieldHint

        binding.buttonSave.setOnClickListener {
            saveRecord()
            finish()
        }

        binding.buttonDelete.setOnClickListener {
            deleteRecord()
            finish()
        }
    }

    private fun deleteRecord() {
        recordPreferences.edit {
            remove("${screenData.record} record")
            remove("${screenData.record} date")
        }
        binding.editTextRecord.text = null
        binding.editTextDate.text = null
    }

    private fun displayRecord() {
        binding.editTextRecord.setText(
            recordPreferences.getString("${screenData.record} record", null)
        )
        binding.editTextDate.setText(
            recordPreferences.getString("${screenData.record} date", null)
        )
    }

    private fun saveRecord() {
        if (screenData.record == null) {
            return
        }
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()
        recordPreferences.edit {
            putString("${this@EditRecordActivity.screenData.record} record", record)
            putString("${this@EditRecordActivity.screenData.record} date", date)
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