package com.example.weather2

class WeatherData(
    val dtTxt: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
) {
    constructor(dtTxt: String, main: com.example.weather2.Main, weather: List<Weather>, wind: Wind)
            : this(dtTxt, Main(main.temp, main.pressure, main.humidity), weather, wind)

    data class Main(
        val temp: Double,
        val pressure: Int,
        val humidity: Int
    )

    data class Weather(
        val description: String,
        val icon: String
    )

    data class Wind(
        val speed: Double,
        val deg: Int,
    )
}