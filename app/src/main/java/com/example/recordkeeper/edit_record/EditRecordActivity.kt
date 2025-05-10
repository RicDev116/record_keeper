package com.example.recordkeeper.edit_record

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recordkeeper.databinding.ActivityEditRecordBinding

const val INTENT_EXTRA_RECORD_DATA = "edit_record_screen_Data"

class EditRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRecordBinding
    private val screenData: EditRecordScreenData by lazy{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(INTENT_EXTRA_RECORD_DATA,EditRecordScreenData::class.java) as EditRecordScreenData
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(INTENT_EXTRA_RECORD_DATA) as EditRecordScreenData
        }
    }
    // lazy means that the variable will be initialized when it is first accessed
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

    private fun displayRecord() {
        binding.editTextRecord.setText(
            recordPreferences.getString("${screenData.record} $SHARED_PREFERENCES_RECORD_KEY", null)
        )
        binding.editTextDate.setText(
            recordPreferences.getString("${screenData.record} $SHARED_PREFERENCES_DATE_KEY", null)
        )
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
            remove("${screenData.record} $SHARED_PREFERENCES_RECORD_KEY")
            remove("${screenData.record} $SHARED_PREFERENCES_DATE_KEY")
        }
        binding.editTextRecord.text = null
        binding.editTextDate.text = null
    }

    private fun saveRecord() {
        if (screenData.record == null) {
            return
        }
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()
        recordPreferences.edit {
            putString("${this@EditRecordActivity.screenData.record} $SHARED_PREFERENCES_RECORD_KEY", record)
            putString("${this@EditRecordActivity.screenData.record} $SHARED_PREFERENCES_DATE_KEY", date)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            //this is the id of the back button in the toolbar
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()//simulate the back button of the phone
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val SHARED_PREFERENCES_RECORD_KEY = "record"
        const val SHARED_PREFERENCES_DATE_KEY = "date"
    }
}