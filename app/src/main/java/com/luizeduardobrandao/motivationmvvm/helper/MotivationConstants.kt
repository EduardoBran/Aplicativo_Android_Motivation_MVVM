package com.luizeduardobrandao.motivation.helper

// adicionar private constructor()
class MotivationConstants private constructor(){

    object KEY {
        const val PERSON_NAME = "personName"
        const val APP_LANGUAGE   = "appLanguage"
    }

    object PHRASEFILTER {
        const val ALL = 0
        const val HAPPY = 1
        const val FUNNY = 2
    }

    // tradução dos dados
    object LANGUAGE {
        const val PORTUGUESE = "pt"
        const val ENGLISH = "en"
        const val FRENCH = "fr"
        const val SPANISH = "es"
    }
}