package com.example.aplikasiizin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class create : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress : EditText
    private lateinit var btnSave: Button
    private lateinit var etYear: EditText
    private lateinit var etDay: EditText
    private lateinit var etMonth : EditText

    private lateinit var dbHelper: UserDataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi komponen
        etUsername = findViewById(R.id.first)
        etDay = findViewById(R.id.day)
        etMonth = findViewById(R.id.month)
        etYear = findViewById(R.id.years)
        etAddress = findViewById(R.id.alamat)
        etEmail = findViewById(R.id.imil)
        btnSave = findViewById(R.id.butam)

        dbHelper = UserDataHelper(this)

        btnSave.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val day = etDay.text.toString().padStart(2, '0')
            val month = etMonth.text.toString().padStart(2, '0')
            val year = etYear.text.toString().trim()
            val etDate = "$day-$month-$year"
            val address = etAddress.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (username.isEmpty() || day.isEmpty() || month.isEmpty() || year.isEmpty() || address.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Semua data wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = dbHelper.insertCustomer(username, etDate, address, email)
            if (success) {
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


