package com.example.aplikasiizin

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class edit : AppCompatActivity() {

    private lateinit var dbHelper: UserDataHelper
    private lateinit var etName: EditText
    private lateinit var etDate: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var btnUpdate: Button

    private var originalEmail: String? = null // Untuk identifikasi data lama

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        dbHelper = UserDataHelper(this)

        etName = findViewById(R.id.etName)
        etDate = findViewById(R.id.etDate)
        etEmail = findViewById(R.id.etEmail)
        etAddress = findViewById(R.id.etAddress)
        btnUpdate = findViewById(R.id.btnUpdate)

        // Ambil data dari intent
        val name = intent.getStringExtra("name")
        val date = intent.getStringExtra("date")
        val email = intent.getStringExtra("email")
        val address = intent.getStringExtra("address")

        // Simpan email asli untuk identifikasi di update
        originalEmail = email

        // Tampilkan data di form
        etName.setText(name)
        etDate.setText(date)
        etEmail.setText(email)
        etAddress.setText(address)

        btnUpdate.setOnClickListener {
            val newName = etName.text.toString()
            val newDate = etDate.text.toString()
            val newEmail = etEmail.text.toString()
            val newAddress = etAddress.text.toString()

            if (originalEmail != null) {
                val updated = dbHelper.updateCustomer(
                    originalEmail!!,
                    newName,
                    newDate,
                    newAddress,
                    newEmail
                )

                if (updated) {
                    Toast.makeText(this, "Berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
