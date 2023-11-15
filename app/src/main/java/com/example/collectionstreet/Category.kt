package com.example.collectionstreet

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Category : AppCompatActivity() {
    companion object {
        private lateinit var addCate: EditText
        private lateinit var addCateButton: ImageView
        private lateinit var cateListView: ListView
        private lateinit var cateList: MutableList<String>
        private lateinit var navBar: BottomNavigationView

        //PoE
        private lateinit var database: FirebaseDatabase
        private lateinit var ref: DatabaseReference
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        navBar = findViewById(R.id.navCate)
        addCate = findViewById(R.id.edtItemsDescription)
        addCateButton = findViewById(R.id.addButtonCate)
        cateListView = findViewById(R.id.lst_Items)

        //PoE
        database = FirebaseDatabase.getInstance()
        ref = database.reference.child("Category")

        cateList = mutableListOf()



        val adapter = ArrayAdapter<String>(this,R.layout.listview_textcolor,cateList)
        cateListView.adapter = adapter


        addCateButton.setOnClickListener(){
            val category = addCate.text.toString()
            cateList.add(category)
            adapter.notifyDataSetChanged()

            ref.child(category).setValue("")
            addCate.text.clear()
        }

        cateListView.setOnItemClickListener { parent, view, position, id ->
            val selectedcategory = cateList[position]
            val toItems = Intent(this, Goal::class.java).also {
                it.putExtra("Selected", selectedcategory)
                startActivity(it)
                finish()
            }
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