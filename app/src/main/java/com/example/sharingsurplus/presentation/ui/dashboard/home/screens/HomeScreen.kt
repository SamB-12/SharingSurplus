package com.example.sharingsurplus.presentation.ui.dashboard.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sharingsurplus.data.states.dashboard.home.HomeScreenUiState
import com.example.sharingsurplus.presentation.ui.dashboard.home.viewmodels.HomeScreenViewModel
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    //homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {

    //val uiState by homeScreenViewModel.homeScreenUiState.collectAsState()
    val uiState = HomeScreenUiState(name = "Phil K")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = uiState.name,
            modifier = modifier
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}