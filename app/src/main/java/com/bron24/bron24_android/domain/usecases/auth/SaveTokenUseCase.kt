package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.repository.TokenRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val tokenRepository: TokenRepository) {
    fun execute(token: String, expiry: Long) {
        tokenRepository.saveToken(token, expiry)
    }
}