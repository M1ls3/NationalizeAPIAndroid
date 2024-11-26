package com.example.nationalizeapiandroid.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nationalizeapiandroid.api.RetrofitInstance
import com.example.nationalizeapiandroid.dao.model.CountryEntity
import com.example.nationalizeapiandroid.dao.model.NationalizeEntity
import com.example.nationalizeapiandroid.data.NationalizeWithCountries
import com.example.nationalizeapiandroid.database.NationalizeDatabase
import com.example.nationalizeapiandroid.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repo
    private val apiService = RetrofitInstance.api

    // LiveData для результатів
    private val _result = MutableLiveData<NationalizeWithCountries>()
    val result: LiveData<NationalizeWithCountries> = _result

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        val nationalizeDao = NationalizeDatabase.getDatabase(application).nationalizeDao()
        repository = Repo(nationalizeDao)
    }

    // Метод для пошуку імені
    fun searchName(name: String) {
        viewModelScope.launch {
            try {
                // Перевірка бази даних
                val cachedResult = repository.getNationalizeWithCountriesByName(name)
                if (cachedResult != null) {
                    // Якщо знайдено, повертаємо результат
                    _result.postValue(cachedResult!!)
                } else {
                    // Якщо не знайдено, викликаємо fetch та save
                    val apiResult = fetchNationalizeData(name)
                    apiResult?.let {
                        _result.postValue(it)
                    } ?: run {
                        _errorMessage.postValue("No data found for name: $name")
                    }
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error: ${e.message}")
            }
        }
    }

    // Метод для отримання даних з API
    private suspend fun fetchNationalizeData(name: String): NationalizeWithCountries? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getNationalizeData(name).execute()
                if (response.isSuccessful && response.body() != null) {
                    val nationalize = response.body()!!

                    // Перетворення API-відповіді у внутрішні об'єкти
                    val nationalizeEntity = NationalizeEntity(
                        name = nationalize.name,
                        count = nationalize.count
                    )

                    saveNationalizeData(nationalizeEntity)
                    val cachedResult = repository.getNationalizeWithCountriesByName(nationalizeEntity.name)

                    val countryEntities = nationalize.country.map { country ->
                        CountryEntity(
                            nationalize_id = cachedResult!!.nationalize.id,
                            country_id = country.country_id,
                            probability = country.probability
                        )
                    }
                    saveCountriesData(countryEntities)

                    NationalizeWithCountries(nationalizeEntity, countryEntities)
                } else {
                    null
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error fetching data: ${e.message}")
                null
            }
        }
    }


    // Метод для збереження даних в базу
    private suspend fun saveNationalizeData(data: NationalizeEntity) {
        try {
            repository.insertNationalize(nationalize = data)
        } catch (e: Exception) {
            _errorMessage.postValue("Error saving Nationalize: ${e.message}")
        }
    }

    // Метод для збереження даних в базу
    private suspend fun saveCountriesData(data: List<CountryEntity>) {
        try {
            repository.insertCountries(countries = data)
        } catch (e: Exception) {
            _errorMessage.postValue("Error saving Countries: ${e.message}")
        }
    }
}

