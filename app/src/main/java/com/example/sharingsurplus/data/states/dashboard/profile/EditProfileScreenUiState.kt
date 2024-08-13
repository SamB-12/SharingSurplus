package com.example.sharingsurplus.data.states.dashboard.profile

import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.Result

data class EditProfileScreenUiState(
    val name: String = "",
    val email: String = "",
    val phone:String = "",
    val address: String = "",
    val imageAddress: String = "",
    val isSuccess: Result<User>? = null,
    val isAlertDialogVisible: Boolean = false,
    val isBackPressed: Boolean = false,
    val isLoading: Boolean = false
)
