package com.example.weather2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather2.R

class HourlyWeatherAdapter(private val hourlyWeatherList: List<HourlyWeather>) : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>() {

    inner class HourlyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hourTextView: TextView = itemView.findViewById<TextView>(R.id.tv_hour)
        private val temperatureTextView: TextView = itemView.findViewById(R.id.tv_temperature)
        private val weatherIconImageView: ImageView = itemView.findViewById(R.id.iv_weather_icon)

        fun bind(hourlyWeather: HourlyWeather) {
            hourTextView.text = hourlyWeather.hour
            temperatureTextView.text = hourlyWeather.temperature
            weatherIconImageView.setImageResource(hourlyWeather.weatherIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hourly_weather_item, parent, false)
        return HourlyWeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val hourlyWeather = hourlyWeatherList[position]
        holder.bind(hourlyWeather)
    }

    override fun getItemCount() = hourlyWeatherList.size
}