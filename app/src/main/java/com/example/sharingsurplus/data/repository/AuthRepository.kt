package com.example.sharingsurplus.data.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    val currentUser: FirebaseUser? // this gives the current user

    suspend fun login(email:String, password:String): AuthResult<FirebaseUser>//this means when a user logs in it will return a result of type FirebaseUser

    suspend fun register(name:String, email: String, password:String): AuthResult<FirebaseUser>

    fun logout()



}