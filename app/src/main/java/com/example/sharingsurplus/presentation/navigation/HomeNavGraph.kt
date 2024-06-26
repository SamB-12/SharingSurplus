package com.example.sharingsurplus.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.dashboard.home.screens.HomeScreen

fun NavGraphBuilder.homeNavGraph(){
    navigation(startDestination = Routes.Home.route, route = Graphs.HomeGraph.graph){
        composable(Routes.Home.route){
            HomeScreen()
        }
    }
}