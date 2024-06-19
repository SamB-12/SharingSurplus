package com.example.sharingsurplus.data.states.auth

import com.example.sharingsurplus.data.repository.AuthResult
import com.google.firebase.auth.FirebaseUser

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isChecked: Boolean = false,
    val isLoading: Boolean = false,
    val authResult: AuthResult<FirebaseUser>? = null
)
