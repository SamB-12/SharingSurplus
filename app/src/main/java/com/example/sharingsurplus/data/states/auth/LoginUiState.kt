package com.example.sharingsurplus.data.states.auth

import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.AuthResult
import com.google.firebase.auth.FirebaseUser

data class LoginUiState(
    val email:String = "",
    val password:String = "",
    val isLoading:Boolean = false,
    val authResult: AuthResult<FirebaseUser>? = null
)
