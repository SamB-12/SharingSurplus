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
import com.example.sharingsurplus.presentation.ui.dashboard.requests.viewmodels.RequestReceivedViewModel

@Composable
fun RequestReceivedScreen(
    modifier: Modifier = Modifier,
    requestReceivedViewModel: RequestReceivedViewModel = hiltViewModel(),
    navController: NavHostController?
) {

    val uiState by requestReceivedViewModel.requestReceivedUiState.collectAsState()

    ScaffoldComponent(
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = "Request Received", onBackClick = {navController?.navigateUp()})},
        content = {
            RequestReceivedScreenMinor(
                requestList = uiState.requestList
            )
        }
    )
}

@Preview
@Composable
private fun RequestReceivedScreenPreview() {
    RequestReceivedScreen(navController = null)
}