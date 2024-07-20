package com.bron24.bron24_android.features.language.domain.model

sealed class Language(val code: String, val displayName: String) {
    class UZBEK : Language("uz", "O'zbek")
    class RUSSIAN : Language("ru", "Russian")

    companion object {
        fun values() = listOf(UZBEK(), RUSSIAN())
    }
}