package com.example.nationalizeapiandroid

class NationalizeRepository(private val nationalizeDao: NationalizeDao) {

    suspend fun getNationalizeByName(name: String): NationalizeEntity? {
        return nationalizeDao.getNationalizeByName(name)
    }

    suspend fun insertNationalize(nationalize: NationalizeEntity): Long {
        return nationalizeDao.insertNationalize(nationalize)
    }

    suspend fun insertCountryInfo(countries: List<CountryInfoEntity>) {
        nationalizeDao.insertCountryInfo(countries)
    }

    suspend fun getAllNationalize(): List<NationalizeEntity> {
        return nationalizeDao.getAllNationalize()
    }
}
