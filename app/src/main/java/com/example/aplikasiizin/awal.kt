package com.example.aplikasiizin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.content.Intent
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView


class awal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_awal)

        val btnBack = findViewById<Button>(R.id.but)
        val imagePaw = findViewById<ImageView>(R.id.pawPopup)
        val imagelo = findViewById<ImageView>(R.id.folo)


        btnBack.setOnClickListener {

            // Load animasi
            val anim = AnimationUtils.loadAnimation(this, R.anim.paw_fly_up)
            val anim2 = AnimationUtils.loadAnimation(this, R.anim.paw_fly_up)


            // Jalankan animasi ke ImageView paw
            imagePaw.startAnimation(anim)
            imagelo.startAnimation(anim2)



            // Setelah animasi selesai, pindah ke MainActivity
            anim.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                override fun onAnimationStart(animation: android.view.animation.Animation) {}

                override fun onAnimationEnd(animation: android.view.animation.Animation) {
                    val intent = Intent(this@awal, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Optional: tutup activity awal
                }

                override fun onAnimationRepeat(animation: android.view.animation.Animation) {}
            })
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}