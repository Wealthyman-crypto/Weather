package com.example.weather2.layoutactivity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.weather2.R

class CitySelectionActivity : AppCompatActivity() {

    private lateinit var etCityName: EditText
    private lateinit var btnSaveCity: Button

    @SuppressLint("MissingInflatedId", "AppCompatMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_selection)

        etCityName = findViewById(R.id.et_search)
        btnSaveCity = findViewById(R.id.btn_search)

        btnSaveCity.setOnClickListener {
            val cityName = etCityName.text.toString()
            val intent = Intent(this@CitySelectionActivity, MainActivity::class.java)
            intent.putExtra("cityName", cityName)
            startActivity(intent)
        }
    }
}