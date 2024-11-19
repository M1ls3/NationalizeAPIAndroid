package com.example.nationalizeapiandroid.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nationalizeapiandroid.R

class NationalizeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    val countTextView: TextView = itemView.findViewById(R.id.countTextView)
    val countryTextView: TextView = itemView.findViewById(R.id.countryTextView)
    val editButton: View = itemView.findViewById(R.id.buttonEdit)
    val deleteButton: View = itemView.findViewById(R.id.buttonDelete)
}
