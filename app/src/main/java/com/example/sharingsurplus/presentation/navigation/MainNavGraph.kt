package com.example.sharingsurplus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.dashboard.home.screens.HomeScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
){
    NavHost(navController = navController, startDestination = Graphs.HomeGraph.graph, route = Graphs.MainGraph.graph){
        homeNavGraph()
        requestsNavGraph()
        communityForumNavGraph(navController)
        profileGraph(navController)
    }
}