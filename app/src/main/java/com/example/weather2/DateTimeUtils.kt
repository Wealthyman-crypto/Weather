package com.example.weather2

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    @SuppressLint("ConstantLocale")
    private val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

    fun getFormattedTimeOfDay(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 0..6 -> "Night"
            in 6..12 -> "Morning"
            in 12..18 -> "Afternoon"
            else -> "Evening"
        }
    }

    fun getFormattedTime(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Date(timestamp * 1000))
    }

    fun getFormattedDate(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = dateFormat.parse(dateString) ?: Date()

        val formattedDateFormat = SimpleDateFormat("dd MMMM yyyy, EEEE, HH:mm", Locale("ru"))
        return formattedDateFormat.format(date)
    }

    fun getFormattedTemperature(temp: Double): String {
        val formattedTemp = String.format("%.0f", temp)
        return "$formattedTemp°C"
    }

    fun getFormattedWind(windSpeed: Double): String {
        val kmPerHour = (windSpeed * 3.6).toInt()
        return "$kmPerHour km/h"
    }

    fun getFormattedDescription(description: String): String {
        val replacements = listOf<Pair<String, String>>(
            "clear sky" to "ясно",
            "few clouds" to "малооблачно",
            "scattered clouds" to "рассеянные облака",
            "broken clouds" to "облачно с прояснениями",
            "shower rain" to "ливневый дождь",
            "rain" to "дождь",
            "thunderstorm" to "гроза",
            "snow" to "снег",
            "mist" to "туман"
        )

        var formattedDescription = description
        for (replacement in replacements) {
            formattedDescription = formattedDescription.replace(replacement.first, replacement.second, true)
        }

        return formattedDescription ?: ""
    }

    fun getFormattedDateWithoutTime(time: Long): String {
        return dateFormat.format(Date(time))
    }

    fun getWeatherIcon(iconCode: String): Int {
        return when (iconCode) {
            "01d" -> R.drawable.icon_sun
            "01n" -> R.drawable.icon_moon
            "02d", "03d", "02n", "03n" -> R.drawable.icon_cloudy
            "04d", "04n" -> R.drawable.icon_cloud
            "09d", "10d", "09n", "10n" -> R.drawable.icon_rain
            "11d", "11n" -> R.drawable.icon_lightning
            "13d", "13n" -> R.drawable.icon_snowy
            else -> R.drawable.ic_launcher_foreground
        }
    }
}