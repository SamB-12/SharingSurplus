package com.example.sharingsurplus.presentation.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object About : Routes("about")
    object Login : Routes("login")
    object Register : Routes("register")

}