package com.example.sharingsurplus.presentation.ui.dashboard.main_menu.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sharingsurplus.presentation.navigation.graphs.MainNavGraph
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.components.BottomNavBarComponent
import com.example.sharingsurplus.presentation.ui.components.TitleTopAppBar
import com.example.sharingsurplus.presentation.ui.dashboard.main_menu.viewmodels.MainMenuViewModel
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    mainMenuViewModel: MainMenuViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    rootNavController: NavHostController?
    ) {
    var pad:PaddingValues?=null

    val uiState by mainMenuViewModel.mainMenuState.collectAsState()

    val localContext = LocalContext.current

    Scaffold(
        modifier = modifier
            .background(color = PrimaryColor),
        topBar = { TitleTopAppBar()},
        bottomBar = {
            BottomNavBarComponent(
                listOfBottomNavItems = uiState.bottomNavItems,
                onItemClick = {mainMenuViewModel.onSelectedItemChanged(it)},
                navController = navController,
                selectedItem = uiState.selectedItem,
                onFabClick = {rootNavController?.navigate(Routes.AddProduce.route)}//change it of course.
            )},
        //floatingActionButton = { FloatingActionButtonComponent()}
    ) {
        MainNavGraph(navController = navController, rootNavController = rootNavController!!,it)
        pad = it
    }
}

@Preview
@Composable
private fun MainMenuScreenPreview() {
    val nav = rememberNavController()
    MainMenuScreen(navController = nav, rootNavController = nav)
}