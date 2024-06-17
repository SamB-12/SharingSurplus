package com.example.sharingsurplus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.auth.screens.LoginScreen
import com.example.sharingsurplus.presentation.ui.auth.screens.RegistrationScreen
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent

@Composable
fun SampleNavGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Login.route){
            LoginScreen(
                painter = painterResource(id = R.drawable.ic_sharing_surplus_logo),
                onSignUpClicked = { navController.navigate(Routes.Register.route) }
                )
        }
        composable(Routes.Register.route){

            RegistrationScreen(
                painter = painterResource(id = R.drawable.ic_sharing_surplus_logo),
                onBackClicked = {navController.navigateUp() },
                onSignInClicked = { navController.navigate(Routes.Login.route) }
            )
        }
    }
}