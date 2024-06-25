package com.example.sharingsurplus.presentation.ui.dashboard.main_menu.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sharingsurplus.data.states.dashboard.main_menu.MainMenuUiState
import com.example.sharingsurplus.presentation.navigation.BottomNavBarItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor (

):ViewModel(){
    private val _mainMenuState = MutableStateFlow(MainMenuUiState())
    val mainMenuState = _mainMenuState.asStateFlow()

    init {
        val listOfItems = listOf<BottomNavBarItems>(
            BottomNavBarItems.Home,
            BottomNavBarItems.CommunityForum,
            BottomNavBarItems.Requests,
            BottomNavBarItems.Profile
        )

        _mainMenuState.value = _mainMenuState.value.copy(bottomNavItems = listOfItems, selectedItem = 0, currentNavItem = BottomNavBarItems.Home)

    }

    fun onSelectedItemChanged(index:Int){
        _mainMenuState.value = _mainMenuState.value.copy(selectedItem = index, currentNavItem = _mainMenuState.value.bottomNavItems[index])
    }
}