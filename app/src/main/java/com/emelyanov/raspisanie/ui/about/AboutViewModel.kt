package com.emelyanov.raspisanie.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Данное приложение было разработанно Никитой Емельяновым.\nВерсия приложения: 1.0\nОбратная связь с разработчиком: nikita.emelyanov@mail.ru"
    }
    val text: LiveData<String> = _text
}