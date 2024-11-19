package com.example.nationalizeapiandroid.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nationalizeapiandroid.ui.nationalize.NationalizeViewModel

class NationalizeViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NationalizeViewModel::class.java)) {
            return NationalizeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}