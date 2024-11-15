package com.example.nationalizeapiandroid

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NationalizeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    val countTextView: TextView = itemView.findViewById(R.id.countTextView)
    val countryTextView: TextView = itemView.findViewById(R.id.countryTextView)
}
