package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.intprog32.caterspot30.Data.CatererData
import com.intprog32.caterspot30.Intefaces.RetrofitInstance

class CatererDetailsActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caterer_details)

        val booking: ImageView = findViewById(R.id.booking_btn)
        val back = findViewById<ImageView>(R.id.back_button)
        val home: ImageView = findViewById(R.id.home_nav)

        val caterer = intent.getParcelableExtra<CatererData>("caterer")
        if (caterer != null) {
            displayCaterer(caterer)
        } else {
            Toast.makeText(this, "Caterer data not available", Toast.LENGTH_SHORT).show()
            finish()
        }


        back.setOnClickListener {
            startActivity(
                Intent(this@CatererDetailsActivity, DashboardActivity::class.java)
            )
        }

        booking.setOnClickListener {
            startActivity(
                Intent(this@CatererDetailsActivity, BookingActivity::class.java)
            )
        }

        home.setOnClickListener {
            startActivity(
                Intent(this@CatererDetailsActivity, DashboardActivity::class.java)
            )
        }
    }

/*    private fun fetchAndDisplayCaterer() {
        RetrofitInstance.api.getCaterers().enqueue(object : retrofit2.Callback<List<CatererData>> {
            override fun onResponse(
                call: retrofit2.Call<List<CatererData>>,
                response: retrofit2.Response<List<CatererData>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val caterers = response.body()!!
                    if (caterers.isNotEmpty()) {
                        val caterer = caterers[0] // or select based on criteria
                        displayCaterer(caterer)
                    } else {
                        Toast.makeText(this@CatererDetailsActivity, "No caterers found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@CatererDetailsActivity, "Failed to load caterers", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<CatererData>>, t: Throwable) {
                Toast.makeText(this@CatererDetailsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }*/

    private fun displayCaterer(caterer: CatererData) {
        findViewById<TextView>(R.id.detail_name).text = caterer.name
        findViewById<TextView>(R.id.detail_desc).text = caterer.description
        val imageView = findViewById<ImageView>(R.id.caterer_image)
        Glide.with(this).load(caterer.imageUrl).into(imageView)
    }
}