package com.example.sharingsurplus.data.repository.auth

import com.example.sharingsurplus.data.repository.Result
import com.google.firebase.auth.FirebaseUser

/**
 * This is an interface for the AuthRepository.
 */
interface AuthRepository {

    val currentUser: FirebaseUser? // this gives the current user

    suspend fun login(email:String, password:String): Result<FirebaseUser>//this means when a user logs in it will return a result of type FirebaseUser

    suspend fun register(name:String, email: String, password:String): Result<FirebaseUser>

    fun logout()



}