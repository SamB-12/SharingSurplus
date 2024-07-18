package com.example.sharingsurplus.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.dashboard.requests.screens.RequestReceivedScreen
import com.example.sharingsurplus.presentation.ui.dashboard.requests.screens.RequestScreen
import com.example.sharingsurplus.presentation.ui.dashboard.requests.screens.RequestSentScreen
import com.example.sharingsurplus.presentation.ui.dashboard.requests.screens.RequestStatusScreen

fun NavGraphBuilder.requestsNavGraph(navController: NavHostController){
    navigation(startDestination = Routes.Requests.route, route = Graphs.RequestGraph.graph){
        composable(route = Routes.Requests.route){
            RequestScreen(navController = navController)
        }
        composable(route = Routes.RequestReceived.route){
            RequestReceivedScreen(navController = navController)
        }
        composable(route = Routes.RequestSent.route){
            RequestSentScreen(navController = navController)
        }
        composable(route = Routes.RequestStatus.route){
            RequestStatusScreen(navController = navController)
        }
    }
}