package com.example.sharingsurplus.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes

sealed class BottomNavBarItems (
    val route: String,
    val selectedIcon: @Composable () -> ImageVector,
    val unselectedIcon: @Composable () -> ImageVector,
    val title: String,
    val hasNews: Boolean = false
){
    object Home : BottomNavBarItems(Graphs.HomeGraph.graph, {Icons.Filled.Home},{Icons.Outlined.Home}, "Home",false)
    object CommunityForum : BottomNavBarItems(Graphs.CommunityGraph.graph, {ImageVector.vectorResource(id = R.drawable.ic_coumminty_fourm_selected_24)},{ImageVector.vectorResource(id = R.drawable.ic_community_fourm_unselected_24)}, "Community",false)
    object Requests: BottomNavBarItems(Graphs.RequestGraph.graph, {ImageVector.vectorResource(id = R.drawable.ic_chat_selected_24)},{ImageVector.vectorResource(id = R.drawable.ic_chat_unselected_24)}, "Requests", false)
    object Profile : BottomNavBarItems(Graphs.ProfileGraph.graph, { Icons.Filled.Face }, {Icons.Outlined.Face},"Profile", false)

}