package com.example.nationalizeapiandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NationalizeAdapterList(private val items: List<NationalizeWithCountries>) : RecyclerView.Adapter<NationalizeViewHolder>() {

    // Створюємо новий ViewHolder (елемент списку)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationalizeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return NationalizeViewHolder(view)
    }

    // Прив'язуємо дані до ViewHolder
    override fun onBindViewHolder(holder: NationalizeViewHolder, position: Int) {
        val item = items[position]
        // Встановлюємо ім'я
        holder.nameTextView.text = "Name: " + item.nationalize.name
        holder.countTextView.text = "Count: " + item.nationalize.count.toString()

        // Створюємо рядок для країн та ймовірностей
        val countryInfoBuilder = StringBuilder()

        // Проходимося по списку країн і додаємо кожну в рядок
        for (countryInfo in item.countries) {
            countryInfoBuilder.append("${countryInfo.country_id} (${countryInfo.probability})\n")
        }

        // Встановлюємо результат у TextView
        holder.countryTextView.text = countryInfoBuilder.toString().trim()
    }

    // Повертає кількість елементів у списку
    override fun getItemCount(): Int = items.size
}
