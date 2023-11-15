package com.example.collectionstreet

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Achievements : AppCompatActivity() {
    private lateinit var navBar: BottomNavigationView
    private lateinit var txtStarter:TextView
    private lateinit var txtCollector:TextView
    private lateinit var txtPackrat:TextView
    private var itemCounter: Int = 0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)

        navBar = findViewById(R.id.navCate)
        txtStarter = findViewById(R.id.tvStarter)
        txtCollector = findViewById(R.id.tvCollector)
        txtPackrat = findViewById(R.id.tvPackrat)
        itemCounter = Items.CounterSingleton.itemCounter

        txtStarter.visibility = if(itemCounter > 0){
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

        txtCollector.visibility = if(itemCounter > 2){
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

        txtPackrat.visibility = if(itemCounter > 9){
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

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
                    val toProgress = Intent(this,Progress::class.java)
                    startActivity(toProgress)
                    finish()
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
                    val toSettings = Intent(this,Settings::class.java)
                    startActivity(toSettings)
                    finish()
                    true
                }

                else -> false
            }

        }
    }
}