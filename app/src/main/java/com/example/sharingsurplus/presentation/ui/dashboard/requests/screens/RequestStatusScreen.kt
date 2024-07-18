package com.example.sharingsurplus.presentation.ui.dashboard.requests.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.requests.viewmodels.RequestStatusViewModel

@Composable
fun RequestStatusScreen(
    modifier: Modifier = Modifier,
    requestStatusViewModel: RequestStatusViewModel = hiltViewModel(),
    navController: NavHostController?
) {

    val uiState by requestStatusViewModel.requestStatusState.collectAsState()

    ScaffoldComponent(
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = "Request Status", onBackClick = {navController?.navigateUp()})},
        content = {
            RequestStatusScreenMinor(
                requestList = uiState.requestList
            )
        }
    )
}

@Preview
@Composable
private fun RequestStatusScreenPreview() {
    RequestStatusScreen(navController = null)
}