package com.example.weather2.layoutactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.weather2.*
import com.example.weather2.Adapters.DailyWeather
import com.example.weather2.Adapters.DailyWeatherPagerAdapter
import com.example.weather2.Adapters.HourlyWeather
import com.example.weather2.Adapters.HourlyWeatherAdapter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var hourlyWeatherList: MutableList<HourlyWeather>
    private lateinit var dailyWeatherList: MutableList<DailyWeather>

    private lateinit var tvCity: TextView
    private lateinit var tvTimeOfDay: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvWind: TextView
    private lateinit var ivWeatherIcon: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager: ViewPager


    @SuppressLint("AppCompatMethod", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        tvCity = findViewById(R.id.tv_city)
        tvTimeOfDay = findViewById(R.id.tv_time_of_day)
        tvTemperature = findViewById(R.id.tv_temperature)
        tvWind = findViewById(R.id.tv_wind)
        ivWeatherIcon = findViewById(R.id.iv_weather_icon)
        recyclerView = findViewById(R.id.rv_hourly_weather)
        viewPager = findViewById(R.id.vp_daily_weather)

        val cityName: String? = intent.getStringExtra("cityName")

        if (cityName != null) {
            loadWeather(cityName)
        }
    }

    private fun loadWeather(cityName: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiKey = "b1c56f7023c8c863cf7c950e7be59b84"

        val weatherApiService = retrofit.create(WeatherApiService::class.java)

        weatherApiService.getForecastForCity(cityName, apiKey).enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                if (response.isSuccessful) {
                    val forecastResponse = response.body()
                    if (forecastResponse != null) {
                        tvCity.text = forecastResponse.city.name
                        tvTimeOfDay.text = DateTimeUtils.getFormattedTimeOfDay()
                        tvTemperature.text = DateTimeUtils.getFormattedTemperature(forecastResponse.list[0].main.temp)
                        tvWind.text = DateTimeUtils.getFormattedWind(forecastResponse.list[0].wind.speed)
                        val forecastCode = forecastResponse.list[0].weather[0].icon
                        val iconResourceId = DateTimeUtils.getWeatherIcon(forecastCode)
                        ivWeatherIcon.setImageResource(iconResourceId)

                        hourlyWeatherList = mutableListOf<HourlyWeather>()
                        for (i in 0 until 8) {
                            val weatherData = forecastResponse.list[i]
                            val hourlyWeather = HourlyWeather(
                                DateTimeUtils.getFormattedTime(weatherData.dt),
                                DateTimeUtils.getFormattedTemperature(weatherData.main.temp),
                                DateTimeUtils.getWeatherIcon(weatherData.weather[0].icon)
                            )
                            hourlyWeatherList.add(hourlyWeather)
                        }

                        dailyWeatherList = mutableListOf<DailyWeather>()
                        for (i in 0 until 7) {
                            if (i * 8 >= forecastResponse.list.size) break

                            var maxTemp = forecastResponse.list[i * 8].main.temp_max
                            var minTemp = forecastResponse.list[i * 8].main.temp_min

                            for (j in 1 until 8) {
                                val index = i * 8 + j
                                if (index >= forecastResponse.list.size) break

                                val weatherData = forecastResponse.list[index]
                                val tempMax = weatherData.main.temp_max
                                val tempMin = weatherData.main.temp_min

                                if (tempMax > maxTemp) {
                                    maxTemp = tempMax
                                }

                                if (tempMin < minTemp) {
                                    minTemp = tempMin
                                }
                            }

                            val weatherData = forecastResponse.list[i * 8]
                            val description = weatherData.weather[0].description
                            val dailyWeather = DailyWeather(
                                DateTimeUtils.getFormattedDate(weatherData.dt_txt),
                                DateTimeUtils.getFormattedTemperature(maxTemp),
                                DateTimeUtils.getFormattedTemperature(minTemp),
                                DateTimeUtils.getFormattedDescription(description),
                                DateTimeUtils.getWeatherIcon(weatherData.weather[0].icon)
                            )
                            dailyWeatherList.add(dailyWeather)
                        }

                        val layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = HourlyWeatherAdapter(hourlyWeatherList)

                        val adapter = DailyWeatherPagerAdapter(dailyWeatherList)
                        viewPager.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}