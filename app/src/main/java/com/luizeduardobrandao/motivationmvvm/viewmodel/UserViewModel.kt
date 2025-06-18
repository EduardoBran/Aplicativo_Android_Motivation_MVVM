package com.luizeduardobrandao.motivationmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luizeduardobrandao.motivationmvvm.helper.MotivationConstants
import com.luizeduardobrandao.motivationmvvm.repository.NamePreferences
import android.app.Application
import androidx.lifecycle.AndroidViewModel


class UserViewModel(application: Application): AndroidViewModel(application) {

    private val prefs = NamePreferences(application)

    // Mensagem de erro (nome vazio)
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // Sinaliza quando deve navegar para MainActivity
    private val _navigateToMain = MutableLiveData<Boolean>()
    val navigateToMain: LiveData<Boolean> = _navigateToMain

    // Se já houver nome salvo, navega imediatamente
    init {
        if (prefs.getStoredString(MotivationConstants.KEY.PERSON_NAME).isNotEmpty()) {
            _navigateToMain.value = true
        }
    }

    // Chamada pela Activity quando o usuário clica em “Salvar”
    fun saveName(name: String) {
        if (name.isBlank()) {
            _errorMessage.value = "Informe seu nome!"
        }
        else {
            prefs.storeString(MotivationConstants.KEY.PERSON_NAME, name)
            _navigateToMain.value = true
        }
    }
}