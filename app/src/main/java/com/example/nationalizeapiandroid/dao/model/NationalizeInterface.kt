package com.example.nationalizeapiandroid.dao.model

interface NationalizeInterface {
    fun getItemType(): Int

    companion object{
        const val Nationalize = 1
        const val Country = 2
    }
}