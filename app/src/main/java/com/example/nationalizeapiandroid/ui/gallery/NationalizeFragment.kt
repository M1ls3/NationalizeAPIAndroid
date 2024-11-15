package com.example.nationalizeapiandroid.ui.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nationalizeapiandroid.NationalizeAdapter
import com.example.nationalizeapiandroid.NationalizeAdapterList
import com.example.nationalizeapiandroid.NationalizeDatabase
import com.example.nationalizeapiandroid.NationalizeWithCountries
import com.example.nationalizeapiandroid.R
import com.example.nationalizeapiandroid.databinding.FragmentNationalizeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NationalizeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NationalizeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_nationalize, container, false)

        recyclerView = rootView.findViewById(R.id.fragmentRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Отримуємо дані з бази даних
        lifecycleScope.launch {
            val dao = NationalizeDatabase.getDatabase(requireContext()).nationalizeDao()
            //val nationalizeList = withContext(Dispatchers.IO) { dao.getAllNationalizeWithCountries() }

            //adapter = NationalizeAdapter(nationalizeList)
            recyclerView.adapter = adapter
        }

        return rootView
    }
}