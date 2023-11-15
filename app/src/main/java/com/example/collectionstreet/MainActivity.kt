package com.example.collectionstreet


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    companion object {
        private lateinit var reg_username: EditText
        private lateinit var reg_email: EditText
        private lateinit var reg_password: EditText
        private lateinit var signUp: Button
        private lateinit var alrAccount: TextView



        //PoE
        private lateinit var auth: FirebaseAuth

        fun user():String{
            val name = reg_username.text.toString()
            return name
        }
    }

    //@SuppressLint("MissingInflatedId")
   // @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reg_username = findViewById(R.id.edt_Rusername)
        reg_email = findViewById(R.id.edt_Remail)
        reg_password = findViewById(R.id.edt_Rpassword)
        signUp = findViewById(R.id.btnSignUp)
        alrAccount = findViewById(R.id.tvAlrAccount)

        //PoE
        auth = Firebase.auth

        signUp.setOnClickListener (){
            val username = reg_username.text.toString()
            val email = reg_email.text.toString()
            val pass = reg_password.text.toString()
            if(username.isNotBlank() && email.isNotBlank()  && pass.isNotBlank()) {
                if(email.contains("@")) {


                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {

                                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT)
                                    .show()

                                val toLogin = Intent(this, Home::class.java)
                                startActivity(toLogin)
                                finish()
                            } else {

                                Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
            else{
                Toast.makeText(this,"Please enter missing field",Toast.LENGTH_SHORT).show()
            }
        }

        alrAccount.setOnClickListener(){
            val log = Intent(this,Login::class.java)
            startActivity(log)
            finish()
        }


    }
}