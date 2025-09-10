package com.example.aplikasiizin


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import androidx.appcompat.app.AlertDialog

class customers : AppCompatActivity() {

    private lateinit var dbHelper: UserDataHelper
    private lateinit var layoutCard: LinearLayout
    private lateinit var buttonCreate: Button
    private lateinit var searchView: SearchView
    private var allCustomers: List<Map<String, String>> = listOf() // untuk menyimpan semua data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customers)

        dbHelper = UserDataHelper(this)
        buttonCreate = findViewById(R.id.tomc)
        layoutCard = findViewById(R.id.customerContainer)
        searchView = findViewById(R.id.cari)

        buttonCreate.setOnClickListener {
            val intent = Intent(this, create::class.java)
            startActivity(intent)
        }

        // Fungsi saat search input berubah
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterData(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterData(newText)
                return true
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        allCustomers = dbHelper.getAllCustomers() // Ambil semua data
        tampilkanDataCustomer(allCustomers)
    }

    private fun tampilkanDataCustomer(customers: List<Map<String, String>>) {
        layoutCard.removeAllViews()

        for (cust in customers) {
            val view = layoutInflater.inflate(R.layout.card_item, null)

            val tvName = view.findViewById<TextView>(R.id.tvName)
            val tvDate = view.findViewById<TextView>(R.id.tvDate)
            val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
            val tvAddress = view.findViewById<TextView>(R.id.tvAddress)
            val btnEdit = view.findViewById<ImageView>(R.id.btnEdit)
            val btnDelete = view.findViewById<ImageView>(R.id.btnDelete)

            tvName.text = cust["name"]
            tvDate.text = "Date of birth: ${cust["date"]}"
            tvEmail.text = "Email: ${cust["email"]}"
            tvAddress.text = "Address: ${cust["address"]}"

            layoutCard.addView(view)

            btnDelete.setOnClickListener {
                val email = cust["email"]
                if (email != null) {
                    AlertDialog.Builder(this)
                        .setTitle("Hapus Data")
                        .setMessage("Yakin ingin menghapus data ini?")
                        .setPositiveButton("Ya") { _, _ ->
                            val sukses = dbHelper.deleteCustomer(email)
                            if (sukses) {
                                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                                allCustomers = dbHelper.getAllCustomers()
                                tampilkanDataCustomer(allCustomers)
                            } else {
                                Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .setNegativeButton("Batal", null)
                        .show()
                }
            }

            btnEdit.setOnClickListener {
                val intent = Intent(this, edit::class.java)
                intent.putExtra("name", cust["name"])
                intent.putExtra("date", cust["date"])
                intent.putExtra("email", cust["email"])
                intent.putExtra("address", cust["address"])
                startActivity(intent)
            }
        }
    }

    // 🔍 Fungsi untuk filter data berdasarkan nama/email
    private fun filterData(query: String?) {
        val filtered = if (query.isNullOrBlank()) {
            allCustomers
        } else {
            allCustomers.filter {
                it["name"]?.contains(query, ignoreCase = true) == true ||
                        it["email"]?.contains(query, ignoreCase = true) == true
            }
        }
        tampilkanDataCustomer(filtered)
    }
}
