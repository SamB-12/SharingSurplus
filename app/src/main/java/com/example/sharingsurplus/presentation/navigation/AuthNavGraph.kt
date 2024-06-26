package com.example.sharingsurplus.presentation.navigation

import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.auth.screens.LoginScreen
import com.example.sharingsurplus.presentation.ui.auth.screens.RegistrationScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(startDestination = Routes.Login.route, route = Graphs.AuthenticationGraph.graph){
        composable(Routes.Login.route){
            LoginScreen(
                painter = painterResource(id = R.drawable.ic_sharing_surplus_logo),
                navController = navController
            )
        }
        composable(Routes.Register.route){
            RegistrationScreen(
                painter = painterResource(id = R.drawable.ic_sharing_surplus_logo),
                onBackClicked = {navController.navigateUp()},
                navController = navController
            )
        }
    }
}