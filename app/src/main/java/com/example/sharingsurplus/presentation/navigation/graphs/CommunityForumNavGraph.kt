package com.example.sharingsurplus.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.dashboard.community_forum.screens.CommunityForumScreen

fun NavGraphBuilder.communityForumNavGraph(
    navController: NavHostController
){
    navigation(startDestination = Routes.CommunityForum.route, route = Graphs.CommunityGraph.graph){
        composable(Routes.CommunityForum.route){
            CommunityForumScreen()
        }
    }
}