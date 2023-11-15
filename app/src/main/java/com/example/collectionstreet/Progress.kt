package com.example.collectionstreet

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class Progress : AppCompatActivity() {

    companion object{
    private lateinit var navBar: BottomNavigationView
    private lateinit var progressBar:ProgressBar
    private var itemCounter: Int = 0
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)


        navBar = findViewById(R.id.navCate)
        try {
            val goal = Goal.getNumGoal()
            progressBar = findViewById(R.id.goalProg)


            if (goal <= 0) {
                // Handle the case when no goal is entered
                Toast.makeText(this, "No goal entered", Toast.LENGTH_SHORT).show()
                // You may choose to finish the activity or take appropriate action
                finish()
                return
            }

            progressBar.max = goal
            itemCounter = Items.CounterSingleton.itemCounter

            ObjectAnimator.ofInt(progressBar, "progress", itemCounter).setDuration(2000).start()

            progressBar.setOnClickListener {
                val results = goal - itemCounter
                Toast.makeText(this, "You are $results item closer to your goal", Toast.LENGTH_SHORT).show()
            }
        } catch (e: UninitializedPropertyAccessException) {
            // Handle the exception when numGoal property is not initialized
            Toast.makeText(this, "Goal not initialized", Toast.LENGTH_SHORT).show()
            // You may choose to finish the activity or take appropriate action
            //finish()
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
                    val manage = Intent(this,ManageAccountSets::class.java)
                    startActivity(manage)
                    finish()
                    true
                }

                else -> false
            }

        }
    }

}