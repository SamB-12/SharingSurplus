package com.example.sharingsurplus.presentation.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharingsurplus.presentation.navigation.graphs.authNavGraph
import com.example.sharingsurplus.presentation.navigation.graphs.communityForumNavGraph
import com.example.sharingsurplus.presentation.navigation.graphs.homeNavGraph
import com.example.sharingsurplus.presentation.navigation.graphs.profileGraph
import com.example.sharingsurplus.presentation.navigation.graphs.requestsNavGraph
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.dashboard.main_menu.screens.MainMenuScreen
import com.example.sharingsurplus.presentation.ui.dashboard.produce.screens.AddProduceScreen

/**
 * This is the root navigation graph of the app.
 */
@Composable
fun SampleNavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = Routes.AuthenticationGraph.route,
    //navController: NavHostController = rememberNavController(),
    ) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        authNavGraph(navController = navController)
        homeNavGraph(navController)
        requestsNavGraph(navController)
        communityForumNavGraph(navController)
        profileGraph(navController)

        composable(Routes.MainMenu.route){
            MainMenuScreen(rootNavController = navController)//store the popped up login value in another stack
        }

        composable(Routes.AddProduce.route){
            AddProduceScreen(navController = navController)
        }
    }
}