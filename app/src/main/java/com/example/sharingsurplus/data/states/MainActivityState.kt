package com.example.sharingsurplus.data.states

import com.example.sharingsurplus.presentation.navigation.utils.Routes

data class MainActivityState(
    val isLoading: Boolean = false,
    val route: String = Routes.AuthenticationGraph.route,
    val isNavHostMain: Boolean = false
)
