package com.example.weather2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.weather2.R

class DailyWeatherPagerAdapter(private val dailyWeatherList: List<DailyWeather>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val dailyWeather = dailyWeatherList[position]
        val itemView = LayoutInflater.from(container.context).inflate(R.layout.daily_weather_item, container, false)

        itemView.findViewById<TextView>(R.id.tv_date).text = dailyWeather.date
        itemView.findViewById<TextView>(R.id.tv_high_temperature).text = dailyWeather.maxTemperature
        itemView.findViewById<TextView>(R.id.tv_low_temperature).text = dailyWeather.minTemperature
        itemView.findViewById<TextView>(R.id.tv_description).text = dailyWeather.description

        val iconImageView = itemView.findViewById<ImageView>(R.id.iv_weather_icon)
        iconImageView.setImageResource(dailyWeather.weatherIcon)

        container.addView(itemView)

        return itemView
    }

    override fun getCount(): Int {
        return dailyWeatherList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return dailyWeatherList[position].date
    }
}