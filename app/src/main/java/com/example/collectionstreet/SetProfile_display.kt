package com.example.collectionstreet

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class SetProfile_display : AppCompatActivity() {
    private lateinit var display:TextView
    private lateinit var navBar: BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setprofile_display)

        navBar = findViewById(R.id.navCate)
        display = findViewById(R.id.tvdisplay)

        display.setOnClickListener{}
        val intent = Intent(this,Settings::class.java)
        startActivity(intent)

        navBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    val home = Intent(this, Home::class.java)
                    startActivity(home)
                    finish()
                    // Handle home button click
                    true
                }
                R.id.nav_category -> {
                    val toCate = Intent(this,Category::class.java)
                    startActivity(toCate)
                    finish()
                    // Handle dashboard button click
                    true
                }
                R.id.nav_progress ->{
                    try {
                        val toProgress = Intent(this, Progress::class.java)
                        startActivity(toProgress)
                        finish()
                    } catch (e: UninitializedPropertyAccessException) {
                        // Handle the exception when the necessary properties are not initialized
                        Toast.makeText(this, "Goal is not initialized", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.nav_achievements -> {
                    val toAchievments = Intent(this,Achievements::class.java)
                    startActivity(toAchievments)
                    finish()
                    // Handle notifications button click
                    true
                }
                R.id.nav_settings ->{
                    val toSettings = Intent(this,ManageAccountSets::class.java)
                    startActivity(toSettings)
                    finish()
                    true
                }

                else -> false
            }

        }
    }
}