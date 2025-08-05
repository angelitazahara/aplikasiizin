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

dbHelper = UserDataHelper(this)
        val username = findViewById< EditText>(R.id.usn)
        val password = findViewById<EditText>(R.id.psw)
        val toms = findViewById<Button>(R.id.toms)

        toms.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()
            if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"username dan password wajib diisi",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val isvalid = dbHelper.checkUser(username,password)
            if (isvalid){
                Toast.makeText(this,"login berhasil", Toast.LENGTH_SHORT).show()
                val  intent = Intent(this,awal::class.java)

                intent.putExtra("username",username)//
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"email atau passord salah", Toast.LENGTH_SHORT).show()
            }
        }



        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}


