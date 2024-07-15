package com.example.sharingsurplus.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.dashboard.community_forum.screens.CommunityForumScreen
import com.example.sharingsurplus.presentation.ui.dashboard.home.screens.HomeScreen
import com.example.sharingsurplus.presentation.ui.dashboard.produce.screens.AddProduceScreen
import com.example.sharingsurplus.presentation.ui.dashboard.profile.screens.ProfileMenuScreen
import com.example.sharingsurplus.presentation.ui.dashboard.requests.screens.RequestScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
    paddingValues: PaddingValues
){
    NavHost(navController = navController, startDestination = Graphs.HomeGraph.graph, route = Graphs.MainGraph.graph){
//        homeNavGraph()
//        requestsNavGraph()
//        communityForumNavGraph(rootNavController)
//        profileGraph(rootNavController)
//        authNavGraph(rootNavController)
        composable(route = Graphs.HomeGraph.graph){
            HomeScreen(paddingValues = paddingValues, navController = rootNavController)
        }
        composable(route = Graphs.CommunityGraph.graph){
            CommunityForumScreen()
        }
        composable(route = Graphs.RequestGraph.graph){
            RequestScreen()
        }
        composable(route = Graphs.ProfileGraph.graph){
            ProfileMenuScreen(navController = rootNavController, paddingValues = paddingValues)
        }
    }
}