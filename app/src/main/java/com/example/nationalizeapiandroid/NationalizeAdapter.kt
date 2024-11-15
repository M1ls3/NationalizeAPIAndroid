package com.example.nationalizeapiandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NationalizeAdapter(private var items: List<NationalizeWithCountries>) :
    RecyclerView.Adapter<NationalizeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationalizeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return NationalizeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NationalizeViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = "Name: " + item.nationalize.name
        holder.countTextView.text = "Count: " + item.nationalize.count

        // Формуємо інформацію про країни
        val countryInfo = item.countries.joinToString("\n") {
            "${it.country_id} (${String.format("%.4f", it.probability)})"
        }
        holder.countryTextView.text = countryInfo
    }

    override fun getItemCount(): Int = items.size

    // Оновлення даних у списку
    fun updateData(newItems: List<NationalizeWithCountries>) {
        items = newItems
        notifyDataSetChanged()
    }
}

