package com.example.collectionstreet


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*


class Items : AppCompatActivity() {
   companion object{
       private lateinit var navBar: BottomNavigationView
       private lateinit var items:TextView
       private lateinit var description:TextView
       private lateinit var edtDes:EditText
       private lateinit var btnCam:ImageView
       private lateinit var imgPic:AppCompatImageView
       private val CAMERA_PERMISSION_CODE = 1
       private val CAMERA_REQUEST_CODE = 2
       private lateinit var database: FirebaseDatabase
       private lateinit var ref: DatabaseReference
       //private var itemCounter = 0
   }

    object CounterSingleton {
        var itemCounter: Int = 0
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        navBar = findViewById(R.id.navCate)
        val selectedcate = intent.getStringExtra("selected")
        items = findViewById<TextView?>(R.id.tv_Items).apply {
            text = selectedcate
        }

        edtDes = findViewById(R.id.edtItemsDescription)
        btnCam = findViewById(R.id.add_btn)

        //PoE
        database = FirebaseDatabase.getInstance()
        ref = database.reference.child("Category")

        btnCam.setOnClickListener{
            val des = edtDes.text.toString()
            description = findViewById<TextView?>(R.id.txtDes).apply {
                text = des
            }
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            ){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }else{
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)

        }else{
            Toast.makeText(this,"You denied permission to use camera, You can grant permission on settings",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imgPic = findViewById(R.id.img_item)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                val thumbNail: Bitmap = data!!.extras!!.get("data") as Bitmap
                imgPic.setImageBitmap(thumbNail)
                uploadImageToFirebaseStorage(thumbNail)
            }
        }
    }

    private fun uploadImageToFirebaseStorage(bitmap: Bitmap) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imagesRef = storageRef.child("images")

        // Create a unique filename for the image
        val fileName = UUID.randomUUID().toString()

        val imageRef = imagesRef.child(fileName + ".jpg")

        // Convert the bitmap to bytes
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        // Upload the image to Firebase Storage
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Image upload successful, retrieve the download URL
            val downloadUrlTask = taskSnapshot.storage.downloadUrl
            downloadUrlTask.addOnSuccessListener { downloadUrl ->
                // Get the download URL of the image
                val imageUrl = downloadUrl.toString()
                // Save the image URL to Firebase Database
                saveImageUrlToFirebaseDatabase(imageUrl)
            }
        }.addOnFailureListener { exception ->
            // Handle the error case
            Toast.makeText(this, "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveImageUrlToFirebaseDatabase(imageUrl: String) {
        val selectedCategory = intent.getStringExtra("selected").toString()
        val description = edtDes.text.toString()

        // Create a new item object with the image URL and description
        val item = Item(imageUrl, description)

        // Get the reference to the category in the database
        val categoryRef = ref.child(selectedCategory)

        // Generate a unique key for the new item
        val newItemKey = categoryRef.push().key

        // Save the item to the database
        categoryRef.child(newItemKey!!).setValue(item)
            .addOnSuccessListener {
                CounterSingleton.itemCounter++
                Toast.makeText(this, "Image and description saved to Firebase", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to save image and description to Firebase: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }



}