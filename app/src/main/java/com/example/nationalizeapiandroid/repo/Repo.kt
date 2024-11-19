package com.example.nationalizeapiandroid.repo

import com.example.nationalizeapiandroid.dao.Dao
import com.example.nationalizeapiandroid.dao.model.CountryEntity
import com.example.nationalizeapiandroid.dao.model.NationalizeEntity

class Repo(private val dao: Dao) {

    suspend fun saveNationalize(nationalizeEntity: NationalizeEntity, countryInfoList: List<CountryEntity>) {
        dao.insertNationalize(nationalizeEntity)
        dao.insertCountryInfo(countryInfoList)
    }

    suspend fun getNationalizeByName(name: String): NationalizeEntity? {
        return dao.getNationalizeByName(name)
    }

    suspend fun getAllNationalize(): List<NationalizeEntity> {
        return dao.getAllNationalize()
    }

    suspend fun updateNationalize(nationalizeEntity: NationalizeEntity) {
        dao.updateNationalize(nationalizeEntity)
    }

    suspend fun deleteNationalize(nationalizeEntity: NationalizeEntity) {
        dao.deleteNationalize(nationalizeEntity)
    }
}