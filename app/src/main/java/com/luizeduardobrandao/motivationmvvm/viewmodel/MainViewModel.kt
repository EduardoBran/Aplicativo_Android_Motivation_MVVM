package com.luizeduardobrandao.motivationmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luizeduardobrandao.motivationmvvm.helper.MotivationConstants
import com.luizeduardobrandao.motivationmvvm.repository.NamePreferences
import com.luizeduardobrandao.motivationmvvm.repository.PhraseRepository
import java.util.Locale
import kotlin.random.Random

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repo = PhraseRepository()
    private val prefs = NamePreferences(application)

    // Saudação
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    // Filtro atual (ALL, HAPPY, FUNNY)
    private val _currentFilter = MutableLiveData<Int>(MotivationConstants.PHRASEFILTER.ALL)
    val currentFilter: LiveData<Int> = _currentFilter

    // Idioma atual
    private val _currentLanguage = MutableLiveData<String>(Locale.getDefault().language)
    val currentLanguage: LiveData<String> = _currentLanguage

    // Frase que deve ser exibida
    private val _currentPhrase = MutableLiveData<String>()
    val currentPhrase: LiveData<String> = _currentPhrase

    // Guarda o índice da frase corrente
    private var currentIndex = 0

    // Carrega nome do usuário e uma frase inicial
    init{
        _userName.value = prefs.getStoredString(MotivationConstants.KEY.PERSON_NAME)
        loadPhrase()
    }

    // Atualiza o filtro e recarrega frase
    fun setFilter(filter: Int){
        _currentFilter.value = filter
        loadPhrase()
    }

    // Avança para nova frase com o mesmo filtro/idioma
    fun nextPhrase() {
        loadPhrase()
    }

    // Seta novo idioma e recarrega frase
    fun setLanguage(code: String) {
        _currentLanguage.value = code
        translateCurrentPhrase()
    }

    // Lógica de obtenção aleatória de frase baseada em filtro + idioma
    private fun loadPhrase(){

        val filter = _currentFilter.value ?: MotivationConstants.PHRASEFILTER.ALL
        val lang = _currentLanguage.value ?: Locale.getDefault().language

        val list = repo.getFilteredPhrases(filter, lang)

        if (list.isNotEmpty()) {
            // escolhe índice aleatório e guarda
            currentIndex = Random.nextInt(list.size)
            _currentPhrase.value = list[currentIndex].description
        }
        else {
            currentIndex = 0
            _currentPhrase.value = ""
        }
    }

    // Refiltra pelo novo idioma e reaplica o mesmo índice salvo
    private fun translateCurrentPhrase() {
        val filter = _currentFilter.value ?: MotivationConstants.PHRASEFILTER.ALL
        val lang   = _currentLanguage.value ?: Locale.getDefault().language
        val list   = repo.getFilteredPhrases(filter, lang)
        // usa o mesmo índice se válido, ou 0 caso contrário
        val idx = if (currentIndex < list.size) currentIndex else 0
        _currentPhrase.value = list.getOrNull(idx)?.description.orEmpty()
    }
}