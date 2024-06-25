package com.example.sharingsurplus.presentation.ui.dashboard.main_menu.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sharingsurplus.presentation.navigation.MainNavGraph
import com.example.sharingsurplus.presentation.ui.components.BottomNavBarComponent
import com.example.sharingsurplus.presentation.ui.dashboard.main_menu.viewmodels.MainMenuViewModel
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    mainMenuViewModel: MainMenuViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
    ) {
    var pad:PaddingValues?=null

    val uiState by mainMenuViewModel.mainMenuState.collectAsState()

    Scaffold(
        modifier = modifier
            .background(color = PrimaryColor),
        bottomBar = {
            BottomNavBarComponent(
                listOfBottomNavItems = uiState.bottomNavItems,
                onItemClick = {mainMenuViewModel.onSelectedItemChanged(it)},
                navController = navController,
                selectedItem = uiState.selectedItem
            )}
    ) {
        MainNavGraph(navController = navController)
        pad = it
    }
}

@Preview
@Composable
private fun MainMenuScreenPreview() {
    val nav = rememberNavController()
    MainMenuScreen(navController = nav)
}