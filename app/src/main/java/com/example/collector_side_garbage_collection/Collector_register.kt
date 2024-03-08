package com.example.collector_side_garbage_collection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Collector_register : AppCompatActivity() {
        lateinit var name:EditText
        lateinit var auth: FirebaseAuth
        lateinit var Email:EditText
        lateinit var Password:EditText
        lateinit var Id:EditText
        lateinit var phone:EditText
        lateinit var signUp:Button
        lateinit var alogin:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collector_register)
        name=findViewById(R.id.PersonName)
        Email=findViewById(R.id.email)
        Password=findViewById(R.id.rpasw)
        phone=findViewById(R.id.phonenumber)
        signUp=findViewById(R.id.register_butt)
        alogin=findViewById(R.id.alreadyaccount)
        auth=FirebaseAuth.getInstance()
        alogin.setOnClickListener {
            val intent= Intent(this@Collector_register,MainActivity::class.java)
            startActivity(intent)

        }
        signUp.setOnClickListener {
            val temail=Email.text.toString()
            val tpas=Password.text.toString()

            signUp(temail,tpas)
        }
    }

    private fun signUp(temail: String, tpas: String) {
        auth.createUserWithEmailAndPassword(temail, tpas)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this@Collector_register,"Account Created",Toast.LENGTH_SHORT).show()
                 val intent=Intent(this@Collector_register,Collector_UI::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user
                Toast.makeText(this@Collector_register,"Fail To Create",Toast.LENGTH_SHORT).show()
                }
            }

    }
}