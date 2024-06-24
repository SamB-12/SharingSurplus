package com.example.sharingsurplus.data.states

import com.example.sharingsurplus.presentation.navigation.Routes

data class MainActivityState(
    val isLoading: Boolean = false,
    val route: String = Routes.Login.route
)
