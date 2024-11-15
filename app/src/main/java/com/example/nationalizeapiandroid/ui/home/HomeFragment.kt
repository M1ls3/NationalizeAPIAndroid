package com.example.nationalizeapiandroid.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nationalizeapiandroid.Nationalize
import com.example.nationalizeapiandroid.NationalizeAdapter
import com.example.nationalizeapiandroid.NationalizeDatabase
import com.example.nationalizeapiandroid.RetrofitInstance
import com.example.nationalizeapiandroid.R
import com.google.android.material.textfield.TextInputEditText

import androidx.lifecycle.lifecycleScope
import com.example.nationalizeapiandroid.CountryInfoEntity
import com.example.nationalizeapiandroid.NationalizeDao
import com.example.nationalizeapiandroid.NationalizeEntity
import com.example.nationalizeapiandroid.NationalizeRepository
import com.example.nationalizeapiandroid.NationalizeWithCountries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NationalizeAdapter
    private lateinit var button: Button
    private lateinit var textInput: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        // Ініціалізація UI елементів
        recyclerView = rootView.findViewById(R.id.recyclerView)
        button = rootView.findViewById(R.id.button)
        textInput = rootView.findViewById(R.id.textInput)

        // Налаштування RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = NationalizeAdapter(emptyList())  // Пустий список для ініціалізації
        recyclerView.adapter = adapter

        // Обробка натискання кнопки
        button.setOnClickListener {
            val name = textInput.text.toString().trim()
            if (name.isNotEmpty()) {
                // Викликаємо suspend функцію всередині корутини
                viewLifecycleOwner.lifecycleScope.launch {
                    fetchAndSaveNationalizeData(name)
                }
            } else {
                Toast.makeText(requireContext(), "Please enter a name", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }

    private suspend fun fetchAndSaveNationalizeData(name: String) {
        val dao = NationalizeDatabase.getDatabase(requireContext()).nationalizeDao()
        val repository = NationalizeRepository(dao)

        // Отримуємо дані з API
        val response = fetchNationalizeData(name)
        if (response != null) {
            // Зберігаємо націоналізацію
            val nationalizeEntity = NationalizeEntity(count = response.count, name = response.name)
            val nationalizeId = repository.insertNationalize(nationalizeEntity).toInt()

            // Створюємо список CountryInfoEntity
            val countryInfoList = response.country.map { country ->
                CountryInfoEntity(
                    nationalize_id = nationalizeId,
                    country_id = country.country_id,
                    probability = country.probability
                )
            }

            // Зберігаємо країни в базу даних
            repository.insertCountryInfo(countryInfoList)

            // Створюємо один елемент для відображення у RecyclerView
            val nationalizeWithCountries = NationalizeWithCountries(nationalizeEntity, countryInfoList)
            adapter.updateData(listOf(nationalizeWithCountries))  // Оновлюємо адаптер для відображення тільки одного елемента
        } else {
            Toast.makeText(requireContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun fetchNationalizeData(name: String): Nationalize? {
        return withContext(Dispatchers.IO) {
            try {
                val call = RetrofitInstance.api.getNationalizeData(name)
                val response = call.execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Exception: ${e.message}")
                null
            }
        }
    }
}

