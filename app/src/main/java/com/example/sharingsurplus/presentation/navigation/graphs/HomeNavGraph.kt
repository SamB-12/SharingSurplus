package com.example.sharingsurplus.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.dashboard.home.screens.HomeScreen
import com.example.sharingsurplus.presentation.ui.dashboard.produce.screens.MapScreen
import com.example.sharingsurplus.presentation.ui.dashboard.produce.screens.ViewAndRequestProduceScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController){
    navigation(startDestination = Routes.Home.route, route = Graphs.HomeGraph.graph){
        composable(Routes.Home.route){
            HomeScreen(navController = navController)
        }
        composable(Routes.ViewAndRequestProduce.route){
            ViewAndRequestProduceScreen(navController = navController)
        }
        composable(Routes.ViewMap.route){
            MapScreen(navController = navController)
        }
    }
}