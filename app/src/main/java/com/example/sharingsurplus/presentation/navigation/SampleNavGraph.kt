package com.example.sharingsurplus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.auth.screens.LoginScreen
import com.example.sharingsurplus.presentation.ui.auth.screens.RegistrationScreen
import com.example.sharingsurplus.presentation.ui.dashboard.home.screens.HomeScreen
import com.example.sharingsurplus.presentation.ui.dashboard.profile.screens.EditProfileScreen
import com.example.sharingsurplus.presentation.ui.dashboard.profile.screens.ProfileInfoScreen
import com.example.sharingsurplus.presentation.ui.dashboard.profile.screens.ProfileKarmaPointsScreen
import com.example.sharingsurplus.presentation.ui.dashboard.profile.screens.ProfileMenuScreen

@Composable
fun SampleNavGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Login.route){
            LoginScreen(
                painter = painterResource(id = R.drawable.ic_sharing_surplus_logo),
                navController = navController
            )
        }
        composable(Routes.Register.route){

            RegistrationScreen(
                painter = painterResource(id = R.drawable.ic_sharing_surplus_logo),
                onBackClicked = {navController.navigateUp() },
                navController = navController
            )
        }
        composable(Routes.Home.route){
            HomeScreen()
        }
        composable(Routes.Profile.route){
            //ProfileScreen(navController = navController)
            ProfileMenuScreen(navController = navController)
        }
        composable(Routes.ProfileInfo.route){
            ProfileInfoScreen()
        }
        composable(Routes.EditProfile.route){
            EditProfileScreen(navController = navController)
        }
        composable(Routes.KarmaPoints.route){
            ProfileKarmaPointsScreen()
        }
    }
}