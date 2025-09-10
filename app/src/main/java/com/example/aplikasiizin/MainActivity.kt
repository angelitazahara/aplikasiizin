package com.example.aplikasiizin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    private lateinit var dbHelper: UserDataHelper
    private lateinit var username : EditText
    private lateinit var password : EditText
    private lateinit var toms : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        dbHelper = UserDataHelper(this)

        username = findViewById(R.id.usn)
        password = findViewById(R.id.psw)
        toms = findViewById(R.id.toms)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toms.setOnClickListener {
            val usernameStr = username.text.toString()
            val passwordStr = password.text.toString()

            if (usernameStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(this, "username dan password wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isvalid = dbHelper.checkUser(usernameStr, passwordStr)
            if (isvalid) {
                Toast.makeText(this, "login berhasil", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, customers::class.java)
                startActivity(intent)

                // Optional: supaya tidak bisa kembali ke login
                // finish()
            } else {
                Toast.makeText(this, "email atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}



