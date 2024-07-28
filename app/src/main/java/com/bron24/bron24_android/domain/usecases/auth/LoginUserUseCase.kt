package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.repository.UserRepository

//class LoginUserUseCase(private val userRepository: UserRepository) {
//
//    fun execute(email: String, password: String): Result<User> {
//        val userResult = userRepository.loginUser(email, password)
//        if (userResult.isSuccess) {
//            val user = userResult.getOrNull()
//            if (user != null && checkPassword(password, user.passwordHash)) {
//                return Result.success(user)
//            }
//        }
//        return Result.failure(Exception("Invalid credentials"))
//    }
//
//    private fun checkPassword(password: String, hashedPassword: String): Boolean {
//        return BCrypt.checkpw(password, hashedPassword)
//    }
//}