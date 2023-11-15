package com.example.collectionstreet

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Goal : AppCompatActivity() {
    companion object {
        private lateinit var numGoal: EditText
        private lateinit var btnGoal: Button


        //PoE
        private lateinit var database: FirebaseDatabase
        private lateinit var goalRef: DatabaseReference
        fun getNumGoal(): Int {
            val goalText = numGoal.text.toString()
            return goalText.toInt()
        }
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)


        numGoal = findViewById(R.id.edt_set_goal)
        btnGoal = findViewById(R.id.btnSetGoal)

        database = FirebaseDatabase.getInstance()
        goalRef = database.reference.child("Goal")

        val selected_category = intent.getStringExtra("Selected")

        btnGoal.setOnClickListener(){
            val goal = numGoal.text.toString()

            val goalNum = goal.toIntOrNull()

            if(goalNum !=null){
                goalRef.child("Your goal for $selected_category is ").setValue(goal)
                Toast.makeText(this, "Your goal is to have $goalNum $selected_category items",Toast.LENGTH_SHORT).show()
                val toItem = Intent(this,Items::class.java).also {
                    it.putExtra("selected",selected_category)
                    startActivity(it)
                    finish()
                }

            }
            else{
                Toast.makeText(this,"Please enter an integer",Toast.LENGTH_SHORT).show()
            }
        }
    }
}