package com.example.nationalizeapiandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nationalizeapiandroid.R
import com.example.nationalizeapiandroid.dao.model.NationalizeEntity
import com.example.nationalizeapiandroid.dao.model.NationalizeInterface
import com.example.nationalizeapiandroid.data.NationalizeWithCountries
import com.example.nationalizeapiandroid.viewholder.NationalizeViewHolder


class NationalizeAdapter(
    private var items: List<NationalizeWithCountries>,
    private val onEditClick: (NationalizeEntity) -> Unit,
    private val onDeleteClick: (NationalizeEntity) -> Unit
) : RecyclerView.Adapter<NationalizeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationalizeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return NationalizeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NationalizeViewHolder, position: Int) {
        val item = items[position]

        // Відображаємо інформацію про націоналізацію
        holder.nameTextView.text = "Name: ${item.nationalize.name}"
        holder.countTextView.text = "Count: ${item.nationalize.count}"

        // Формуємо рядок з інформацією про країни
        val countryInfo = item.countries.joinToString("\n") { country ->
            "${country.country_id} (${country.probability})"
        }
        holder.countryTextView.text = countryInfo

        // Логіка для кнопок редагування та видалення
        holder.editButton.setOnClickListener {
            onEditClick(item.nationalize)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(item.nationalize)
        }
    }

    override fun getItemCount(): Int = items.size

    // Оновлення даних у списку
    fun updateData(newItems: List<NationalizeWithCountries>) {
        items = newItems
        notifyDataSetChanged()
    }
}
