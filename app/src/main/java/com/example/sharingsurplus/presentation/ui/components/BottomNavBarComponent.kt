package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sharingsurplus.data.states.dashboard.main_menu.MainMenuUiState
import com.example.sharingsurplus.presentation.navigation.BottomNavBarItems
import com.example.sharingsurplus.presentation.ui.theme.AccentColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryTextColor

@Composable
fun BottomNavBarComponent(
    modifier: Modifier = Modifier,
    listOfBottomNavItems: List<BottomNavBarItems>,
    selectedItem: Int,
    navController: NavHostController?,
    onItemClick: (Int) -> Unit = {},
    onFabClick: () -> Unit = {}
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

//    NavigationBar(
//        modifier = modifier
//            .fillMaxWidth()
//            .background(color = SecondaryColor),
//        tonalElevation = 8.dp,
//        containerColor = SecondaryColor
//    ) {
//        listOfBottomNavItems.forEachIndexed { index, bottomNavBarItems ->
//            NavigationBarItem(
//                selected = selectedItem == index,
//                label = { Text(text = bottomNavBarItems.title)},
//                onClick = {
//                    onItemClick(index)
//                    navController?.navigate(bottomNavBarItems.route)
//                },
//                icon = {
//                    BadgedBox(
//                        badge = {
//                            if (bottomNavBarItems.hasNews){
//                                Badge()
//                            }
//                        }
//                    ) {
//                        if (selectedItem == index) {
//                            Icon(imageVector = bottomNavBarItems.selectedIcon(), contentDescription = bottomNavBarItems.title)
//                        } else {
//                            Icon(imageVector = bottomNavBarItems.unselectedIcon(), contentDescription = bottomNavBarItems.title)
//                        }
//
//                    }
//                },
//                colors = NavigationBarItemDefaults.colors(
//                    //selectedIconColor = AccentColor,
//                    selectedTextColor = Color.White,
//                    indicatorColor = PrimaryColor
//                )
//            )
//        }
//    }

//    Box(
//        modifier = modifier.fillMaxWidth()
//    ){
//        Row(
//            modifier = modifier
//                .fillMaxWidth()
//                .background(color = SecondaryColor),
//                //.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            listOfBottomNavItems.subList(0,2).forEachIndexed { index, bottomNavBarItems ->
//                NavigationBarItem(
//                    selected = selectedItem == index,
//                    label = { Text(text = bottomNavBarItems.title)},
//                    onClick = {
//                        onItemClick(index)
//                        navController?.navigate(bottomNavBarItems.route)
//                    },
//                    icon = {
//                        BadgedBox(
//                            badge = {
//                                if (bottomNavBarItems.hasNews){
//                                    Badge()
//                                }
//                            }
//                        ) {
//                            if (selectedItem == index) {
//                                Icon(imageVector = bottomNavBarItems.selectedIcon(), contentDescription = bottomNavBarItems.title)
//                            } else {
//                                Icon(imageVector = bottomNavBarItems.unselectedIcon(), contentDescription = bottomNavBarItems.title)
//                            }
//
//                        }
//                    },
//                    colors = NavigationBarItemDefaults.colors(
//                        //selectedIconColor = AccentColor,
//                        selectedTextColor = Color.White,
//                        indicatorColor = PrimaryColor
//                    )
//                )
//            }
//
//            Spacer(modifier = modifier.width(56.dp))
//
//            listOfBottomNavItems.subList(2,4).forEachIndexed { index, bottomNavBarItems ->
//                NavigationBarItem(
//                    selected = selectedItem == index + 2,
//                    label = { Text(text = bottomNavBarItems.title)},
//                    onClick = {
//                        onItemClick(index + 2)
//                        navController?.navigate(bottomNavBarItems.route)
//                    },
//                    icon = {
//                        BadgedBox(
//                            badge = {
//                                if (bottomNavBarItems.hasNews){
//                                    Badge()
//                                }
//                            }
//                        ) {
//                            if (selectedItem == index + 2) {
//                                Icon(imageVector = bottomNavBarItems.selectedIcon(), contentDescription = bottomNavBarItems.title)
//                            } else {
//                                Icon(imageVector = bottomNavBarItems.unselectedIcon(), contentDescription = bottomNavBarItems.title)
//                            }
//
//                        }
//                    },
//                    colors = NavigationBarItemDefaults.colors(
//                        //selectedIconColor = AccentColor,
//                        selectedTextColor = Color.White,
//                        indicatorColor = PrimaryColor
//                    )
//                )
//            }
//        }
////        NavigationBar(
////            modifier = modifier
////                .fillMaxWidth()
////                .background(color = SecondaryColor),
////            tonalElevation = 8.dp,
////            containerColor = SecondaryColor
////        ) {
////
////        }
//
//        FloatingActionButton(
//            onClick = { /*TODO*/ },
//            modifier = modifier
//                .align(Alignment.Center)
//                .offset(y = -24.dp),
//            containerColor = AccentColor
//        ) {
//            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
//        }
//    }

    Box(
        modifier = modifier.fillMaxWidth()
    ){
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp)
                .background(color = PrimaryColor),
            tonalElevation = 12.dp,
            containerColor = PrimaryColor
        ) {
            listOfBottomNavItems.subList(0,2).forEachIndexed { index, bottomNavBarItems ->
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
                        selectedIconColor = SecondaryColor,
                        selectedTextColor = SecondaryTextColor,
                        indicatorColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.width(56.dp))

            listOfBottomNavItems.subList(2,4).forEachIndexed { index, bottomNavBarItems ->
                NavigationBarItem(
                    selected = selectedItem == index + 2,
                    label = { Text(text = bottomNavBarItems.title)},
                    onClick = {
                        onItemClick(index + 2)
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
                            if (selectedItem == index + 2) {
                                Icon(imageVector = bottomNavBarItems.selectedIcon(), contentDescription = bottomNavBarItems.title)
                            } else {
                                Icon(imageVector = bottomNavBarItems.unselectedIcon(), contentDescription = bottomNavBarItems.title)
                            }

                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SecondaryColor,
                        selectedTextColor = SecondaryTextColor,
                        indicatorColor = Color.Transparent
                        //indicatorColor = PrimaryColor
                    )
                )
            }
        }

        Box (
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -40.dp)
        ){
            FloatingActionButton(
                onClick = { onFabClick() },
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = -24.dp),
                containerColor = SecondaryColor
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun BottomNavBarComponentPreview() {
    BottomNavBarComponent(listOfBottomNavItems = listOf(BottomNavBarItems.Home, BottomNavBarItems.CommunityForum), navController = null, selectedItem = 0)
}