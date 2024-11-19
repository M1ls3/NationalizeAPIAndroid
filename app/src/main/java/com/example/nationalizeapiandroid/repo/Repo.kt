package com.example.nationalizeapiandroid.repo

import com.example.nationalizeapiandroid.dao.Dao
import com.example.nationalizeapiandroid.dao.model.CountryEntity
import com.example.nationalizeapiandroid.dao.model.NationalizeEntity
import com.example.nationalizeapiandroid.data.NationalizeWithCountries

class Repo(private val dao: Dao) {

    // Вставка нової націоналізації разом із країнами
    suspend fun insertNationalizeWithCountries(
        nationalize: NationalizeEntity,
        countries: List<CountryEntity>
    ) {
        dao.insertNationalize(nationalize)
        dao.insertCountries(countries)
    }

    // Оновлення націоналізації та пов'язаних країн
    suspend fun updateNationalizeWithCountries(
        nationalize: NationalizeEntity,
        countries: List<CountryEntity>
    ) {
        dao.updateNationalize(nationalize)
        dao.deleteCountriesByNationalizeId(nationalize.id) // Видаляємо старі країни
        dao.insertCountries(countries) // Додаємо нові
    }

    // Видалення націоналізації та всіх пов'язаних країн
    suspend fun deleteNationalize(nationalize: NationalizeEntity) {
        dao.deleteNationalize(nationalize) // Через cascade будуть видалені пов'язані країни
    }

    // Отримання націоналізації та країн по імені
    suspend fun getNationalizeWithCountriesByName(name: String): NationalizeWithCountries? {
        return dao.getNationalizeWithCountriesByName(name)
    }

    // Отримання всіх націоналізацій з країнами
    suspend fun getAllNationalizeWithCountries(): List<NationalizeWithCountries> {
        return dao.getAllNationalizeWithCountries()
    }
}
