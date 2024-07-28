package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.repository.UserRepository

//class RegisterUserUseCase(private val userRepository: UserRepository) {

//    fun execute(user: User): Result<User> {
//        val hashedPassword = hashPassword(user.passwordHash)
//        val userWithHashedPassword = user.copy(passwordHash = hashedPassword)
//        return userRepository.registerUser(userWithHashedPassword)
//    }

//    private fun hashPassword(password: String): String {
//        return BCrypt.hashpw(password, BCrypt.gensalt())
//    }
//}