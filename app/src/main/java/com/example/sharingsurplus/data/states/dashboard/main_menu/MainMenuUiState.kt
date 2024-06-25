package com.example.sharingsurplus.data.states.dashboard.main_menu

import com.example.sharingsurplus.presentation.navigation.BottomNavBarItems
import com.example.sharingsurplus.presentation.navigation.utils.Graphs

data class MainMenuUiState(
    val bottomNavItems: List<BottomNavBarItems> = emptyList(),
    val currentNavItem: BottomNavBarItems = BottomNavBarItems.Home,
    val selectedItem: Int = 0,
    //val navigateTo: String = Graphs.HomeGraph.graph
)
