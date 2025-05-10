package com.example.recordkeeper

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.recordkeeper.cycling.CyclingFragment
import com.example.recordkeeper.databinding.ActivityMainBinding
import com.example.recordkeeper.running.RunningFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), View.OnClickListener,
    NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.green_700)
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // Force light theme
        // "this" hace referencia a la interface View.OnClickListener
        binding.running.setOnClickListener(this)
        // Se puede hacer lo mismo con una clase anónima(object : View.OnClickListener)
        // al igual que se hace en java
        //todo esto se hace detrás de escenas con el uso de lambdas
        binding.cycling.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                Toast.makeText(
                    this@MainActivity,
                    "This is nav cycling from anonymous class",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        // "this" hace referencia a la interface NavigationBarView.OnItemSelectedListener
        binding.bottomNavigationView.setOnItemSelectedListener(this)
        //esto es lo mismo que lo de arriba pero con lambdas
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.navigation_running -> {
//                    onRunningClicked()
//                    true
//                }
//
//                R.id.navigation_cycling -> {
//                    onCyclingClicked()
//                    true
//                }
//
//                else -> false
//            }
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuClickHandled = when (item.itemId) {
            R.id.reset_running -> {
                showConfirmationDIalog(RUNNING_DISPLAY_VALUE)
                true
            }
            R.id.reset_cycling -> {
                showConfirmationDIalog(CYCLING_DISPLAY_VALUE)
                true
            }
            R.id.reset_all -> {
                showConfirmationDIalog(ALL_DISPLAY_VALUE)
                true
            }
            else -> false
        }
        return menuClickHandled
    }

    private fun showConfirmationDIalog(selection: String) {
        AlertDialog.Builder(this)
            .setTitle("Reset $selection Records")
            .setMessage(
                "Are you sure you want to reset $selection records?"
            )
            .setPositiveButton("Yes") { _, _ ->
                when (selection) {
                    ALL_DISPLAY_VALUE -> {
                        getSharedPreferences(RUNNING_DISPLAY_VALUE, MODE_PRIVATE).edit { clear() }
                        getSharedPreferences(CYCLING_DISPLAY_VALUE, MODE_PRIVATE).edit { clear() }
                    }
                    RUNNING_DISPLAY_VALUE -> getSharedPreferences(RunningFragment.FILE_NAME, MODE_PRIVATE)
                        .edit { clear() }
                    CYCLING_DISPLAY_VALUE -> getSharedPreferences(CyclingFragment.FILE_NAME, MODE_PRIVATE)
                        .edit { clear() }
                }
                refreshCurrentFragment()
                showConfirmation()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showConfirmation() {
        val snackbar = Snackbar.make(
            binding.root,
            "Records reset successfully",
            Snackbar.LENGTH_SHORT
        )
        snackbar.anchorView = binding.bottomNavigationView
        snackbar.setAction("Undo") {
            refreshCurrentFragment()
        }
        snackbar.show()
    }

    private fun refreshCurrentFragment() {
        //refresh del fragment
        when (binding.bottomNavigationView.selectedItemId) {
            R.id.navigation_running -> onRunningClicked()
            R.id.navigation_cycling -> onCyclingClicked()
            else -> {}
        }
    }

    private fun onRunningClicked():Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, RunningFragment())
        }
        return true
    }

    private fun onCyclingClicked():Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, CyclingFragment())
        }
        return true
    }

    //Esta función es llamada cuando se hace click en un elemento de la vista
    //se puede tener toda la lógica de la vista aquí y los clicks se manejan
    override fun onClick(view: View?) {
        if (view?.id == R.id.running) {
            Toast.makeText(this, "This is nav running from onClickMethod", Toast.LENGTH_SHORT)
                .show()
        } else if (view?.id == R.id.cycling) {
            Toast.makeText(this, "This is nav cycling from onClickMethod", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.navigation_running -> onRunningClicked()
        R.id.navigation_cycling -> onCyclingClicked()
        else -> false
    }

    companion object{
        const val RUNNING_DISPLAY_VALUE = "running_records"
        const val CYCLING_DISPLAY_VALUE = "cycling_records"
        const val ALL_DISPLAY_VALUE = "all"
    }

}