package com.example.sharingsurplus.presentation.ui.dashboard.profile.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sharingsurplus.data.states.dashboard.profile.ProfileKarmaPointsScreenUiState
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels.ProfileKarmaPointsViewModel

@Composable
fun ProfileKarmaPointsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    profileKarmaPointsViewModel: ProfileKarmaPointsViewModel = hiltViewModel(),
    navController: NavHostController?
) {

    //val uiState = ProfileKarmaPointsScreenUiState(5)

    val uiState by profileKarmaPointsViewModel.karmaPointsUiState.collectAsState()

    ScaffoldComponent(
        modifier = modifier,
        topBar = { TopAppBarWithBackComponent(title = "Good Deed Points", onBackClick = { navController?.navigateUp()})},
        content = { ProfileKarmaPointsScreenMinor(karmaPoints = uiState.karmaPoints)}
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileKarmaPointsScreenPreview() {
    ProfileKarmaPointsScreen(navController = null)
}
