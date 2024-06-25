package com.example.sharingsurplus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.dashboard.main_menu.screens.MainMenuScreen

@Composable
fun SampleNavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = Routes.AuthenticationGraph.route,
    //navController: NavHostController = rememberNavController(),
    ) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        authNavGraph(navController = navController)

        composable(Routes.MainMenu.route){
            MainMenuScreen()
        }
    }
}