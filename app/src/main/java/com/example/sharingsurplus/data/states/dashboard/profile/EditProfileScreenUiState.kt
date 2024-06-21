package com.example.sharingsurplus.data.states.dashboard.profile

import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.AuthResult

data class EditProfileScreenUiState(
    val name: String = "",
    val email: String = "",
    val phone:String = "",
    val address: String = "",
    val imageAddress: String = "",
    val isSuccess: AuthResult<User>? = null
)
