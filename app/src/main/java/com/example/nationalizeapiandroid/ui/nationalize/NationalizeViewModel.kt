package com.example.nationalizeapiandroid.ui.nationalize

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nationalizeapiandroid.dao.model.CountryEntity
import com.example.nationalizeapiandroid.dao.model.NationalizeEntity
import com.example.nationalizeapiandroid.data.NationalizeWithCountries
import com.example.nationalizeapiandroid.database.NationalizeDatabase
import com.example.nationalizeapiandroid.repo.Repo
import kotlinx.coroutines.launch

class NationalizeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repo

    // MutableLiveData для спостереження за списком NationalizeWithCountries
    private val _nationalizeItems = MutableLiveData<List<NationalizeWithCountries>>()
    val nationalizeItems: LiveData<List<NationalizeWithCountries>> = _nationalizeItems

    // LiveData для відображення помилок
    private val _errorMessage = MutableLiveData<String>()

    init {
        val nationalizeDao = NationalizeDatabase.getDatabase(application).nationalizeDao()
        repository = Repo(nationalizeDao)
        loadItems() // Завантажуємо дані при ініціалізації
    }

    // Завантаження всіх елементів
    fun loadItems() {
        viewModelScope.launch {
            try {
                val items = repository.getAllNationalizeWithCountries()
                _nationalizeItems.postValue(items)
            } catch (e: Exception) {
                _errorMessage.postValue("Error loading data: ${e.message}")
            }
        }
    }

    // Оновлення націоналізації та країн
    fun updateNationalize(nationalize: NationalizeEntity, countries: List<CountryEntity>) {
        viewModelScope.launch {
            try {
                repository.updateNationalizeWithCountries(nationalize, countries)
                loadItems() // Оновлюємо список після оновлення
            } catch (e: Exception) {
                _errorMessage.postValue("Error updating data: ${e.message}")
            }
        }
    }

        // Видалення націоналізації
        fun deleteNationalize(nationalize: NationalizeEntity) {
            viewModelScope.launch {
                try {
                    repository.deleteNationalize(nationalize)
                    loadItems() // Оновлюємо список після видалення
                } catch (e: Exception) {
                    _errorMessage.postValue("Error deleting data: ${e.message}")
                }
            }
        }
}
