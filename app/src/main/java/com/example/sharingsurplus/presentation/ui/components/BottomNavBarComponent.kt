package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.sharingsurplus.data.states.dashboard.main_menu.MainMenuUiState
import com.example.sharingsurplus.presentation.navigation.BottomNavBarItems
import com.example.sharingsurplus.presentation.ui.theme.AccentColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor

@Composable
fun BottomNavBarComponent(
    modifier: Modifier = Modifier,
    listOfBottomNavItems: List<BottomNavBarItems>,
    selectedItem: Int,
    navController: NavHostController?,
    onItemClick: (Int) -> Unit = {}
) {

    //val uiState = MainMenuUiState(listOf(BottomNavBarItems.Home, BottomNavBarItems.CommunityForum),0)

//    var selectedItem by rememberSaveable { mutableIntStateOf(0)}//should come from the view model
//
//    val bottomNavItems = listOf<BottomNavBarItems>(
//        BottomNavBarItems.Home,
//        BottomNavBarItems.CommunityForum,
//        BottomNavBarItems.Requests,
//        BottomNavBarItems.Profile,
//    ) // add this to the view model as well

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .background(color = SecondaryColor),
        containerColor = SecondaryColor
    ) {
        listOfBottomNavItems.forEachIndexed { index, bottomNavBarItems ->
            NavigationBarItem(
                selected = selectedItem == index,
                label = { Text(text = bottomNavBarItems.title)},
                onClick = {
                    onItemClick(index)
                    navController?.navigate(bottomNavBarItems.route)
                          },
                icon = {
                    BadgedBox(
                        badge = {
                            if (bottomNavBarItems.hasNews){
                                Badge()
                            }
                        }
                    ) {
                        if (selectedItem == index) {
                            Icon(imageVector = bottomNavBarItems.selectedIcon(), contentDescription = bottomNavBarItems.title)
                        } else {
                            Icon(imageVector = bottomNavBarItems.unselectedIcon(), contentDescription = bottomNavBarItems.title)
                        }

                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    //selectedIconColor = AccentColor,
                    selectedTextColor = Color.White,
                    indicatorColor = PrimaryColor
                )
            )
        }
    }
}

@Preview
@Composable
private fun BottomNavBarComponentPreview() {
    BottomNavBarComponent(listOfBottomNavItems = listOf(BottomNavBarItems.Home, BottomNavBarItems.CommunityForum), navController = null, selectedItem = 0)
}