package com.example.collector_side_garbage_collection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var textpress: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginbtn: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        textpress = findViewById(R.id.newuser)
        email = findViewById(R.id.EmailLogin)
        password = findViewById(R.id.Loginpassword)
        loginbtn = findViewById(R.id.login)
        textpress.setOnClickListener {
            val intent = Intent(this, Collector_register::class.java)
            startActivity(intent)
        }
        loginbtn.setOnClickListener {
            val trueEmail = email.text.toString()
            val truePassword = password.text.toString()
            if (trueEmail.isNotEmpty() && truePassword.isNotEmpty()) {
                login(trueEmail, truePassword)
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(trueEmail: String, truePassword: String) {
        mAuth.signInWithEmailAndPassword(trueEmail, truePassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, Collector_UI::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication failed, please check your credentials", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
