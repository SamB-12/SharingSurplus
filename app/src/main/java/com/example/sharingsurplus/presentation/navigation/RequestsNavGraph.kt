package com.example.sharingsurplus.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.dashboard.requests.screens.RequestScreen

fun NavGraphBuilder.requestsNavGraph(){
    navigation(startDestination = Routes.Requests.route, route = Graphs.RequestGraph.graph){
        composable(route = Routes.Requests.route){
            RequestScreen()
        }
    }
}