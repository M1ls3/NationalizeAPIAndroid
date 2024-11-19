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
    val errorMessage: LiveData<String> = _errorMessage

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

    // Додавання нової націоналізації з країнами
    fun addNationalize(nationalize: NationalizeEntity, countries: List<CountryEntity>) {
        viewModelScope.launch {
            try {
                repository.insertNationalizeWithCountries(nationalize, countries)
                loadItems() // Оновлюємо список після додавання
            } catch (e: Exception) {
                _errorMessage.postValue("Error adding data: ${e.message}")
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

    // Приватний метод для завантаження одного елемента по імені (можна використати при пошуку)
    private fun loadItemByName(name: String) {
        viewModelScope.launch {
            try {
                val item = repository.getNationalizeWithCountriesByName(name)
                if (item != null) {
                    _nationalizeItems.postValue(listOf(item)) // Якщо знайдено, встановлюємо список із одним елементом
                } else {
                    _errorMessage.postValue("Item not found")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error loading data: ${e.message}")
            }
        }
    }

    fun simpleData() {
        viewModelScope.launch {
            try {
                // Створення списку націоналізацій з відповідними країнами
                val nationalize1 = NationalizeEntity(id = 1, name = "John", count = 100)
                val countries1 = listOf(
                    CountryEntity(nationalize_id = 1, country_id = "US", probability = 0.4),
                    CountryEntity(nationalize_id = 1, country_id = "GB", probability = 0.3),
                    CountryEntity(nationalize_id = 1, country_id = "AU", probability = 0.15),
                    CountryEntity(nationalize_id = 1, country_id = "CA", probability = 0.1),
                    CountryEntity(nationalize_id = 1, country_id = "IE", probability = 0.05)
                )

                val nationalize2 = NationalizeEntity(id = 2, name = "Alice", count = 150)
                val countries2 = listOf(
                    CountryEntity(nationalize_id = 2, country_id = "FR", probability = 0.5),
                    CountryEntity(nationalize_id = 2, country_id = "DE", probability = 0.25),
                    CountryEntity(nationalize_id = 2, country_id = "BE", probability = 0.1),
                    CountryEntity(nationalize_id = 2, country_id = "NL", probability = 0.1),
                    CountryEntity(nationalize_id = 2, country_id = "LU", probability = 0.05)
                )

                val nationalize3 = NationalizeEntity(id = 3, name = "Maria", count = 200)
                val countries3 = listOf(
                    CountryEntity(nationalize_id = 3, country_id = "ES", probability = 0.6),
                    CountryEntity(nationalize_id = 3, country_id = "PT", probability = 0.2),
                    CountryEntity(nationalize_id = 3, country_id = "AR", probability = 0.1),
                    CountryEntity(nationalize_id = 3, country_id = "BR", probability = 0.05),
                    CountryEntity(nationalize_id = 3, country_id = "IT", probability = 0.05)
                )

                // Додавання до бази даних через репозиторій
                repository.insertNationalizeWithCountries(nationalize1, countries1)
                repository.insertNationalizeWithCountries(nationalize2, countries2)
                repository.insertNationalizeWithCountries(nationalize3, countries3)

                // Оновлення списку після додавання даних
                loadItems()

            } catch (e: Exception) {
                _errorMessage.postValue("Error populating database: ${e.message}")
            }
        }
    }

}
