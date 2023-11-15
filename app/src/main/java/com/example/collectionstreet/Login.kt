package com.example.collectionstreet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    companion object {
        //private lateinit var log_name: EditText
        private lateinit var log_email: EditText
        private lateinit var log_password: EditText
        private lateinit var login: Button


        //PoE
        private lateinit var auth: FirebaseAuth
        private lateinit var forgot:TextView
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        log_email = findViewById(R.id.edt_lEmail)
        log_password = findViewById(R.id.edt_lPassword)
        login = findViewById(R.id.btnLogin)


        //PoE
        auth = Firebase.auth
        forgot = findViewById(R.id.tvForgot)

        login.setOnClickListener(){
            //val username = log_name.text.toString()
            val email = log_email.text.toString()
            val password = log_password.text.toString()



            if(email.isNotBlank() && password.isNotBlank()) {

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(this,"Successfully Logged In", Toast.LENGTH_SHORT).show()
                            val toHome = Intent(this,Home::class.java)
                            startActivity(toHome)
                            finish()
                        } else {

                            Toast.makeText(this,"Please enter correct login details", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                Toast.makeText(this,"Please fill in empty fields",Toast.LENGTH_SHORT).show()
            }
        }

        forgot.setOnClickListener{
            val email = log_email.text.toString().trim()

            if (email.isNotEmpty()) {
                resetPassword(email)
            } else {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to send reset email: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}