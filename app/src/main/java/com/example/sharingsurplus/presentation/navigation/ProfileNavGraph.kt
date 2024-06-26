package com.example.sharingsurplus.presentation.navigation

import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.dashboard.profile.screens.EditProfileScreen
import com.example.sharingsurplus.presentation.ui.dashboard.profile.screens.ProfileAboutUsScreen
import com.example.sharingsurplus.presentation.ui.dashboard.profile.screens.ProfileInfoScreen
import com.example.sharingsurplus.presentation.ui.dashboard.profile.screens.ProfileKarmaPointsScreen
import com.example.sharingsurplus.presentation.ui.dashboard.profile.screens.ProfileMenuScreen

fun NavGraphBuilder.profileGraph(navController: NavHostController) {
    navigation(startDestination = Routes.Profile.route, route = Graphs.ProfileGraph.graph){
        composable(Routes.Profile.route){
            //ProfileScreen(navController = navController)
            ProfileMenuScreen(navController = navController, paddingValues = null)
        }
        composable(Routes.ProfileInfo.route){
            ProfileInfoScreen(navController = navController)
        }
        composable(Routes.EditProfile.route){
            EditProfileScreen(navController = navController)
        }
        composable(Routes.KarmaPoints.route){
            ProfileKarmaPointsScreen(navController = navController)
        }
        composable(Routes.AboutUs.route){
            ProfileAboutUsScreen(navController = navController)
        }
    }
}