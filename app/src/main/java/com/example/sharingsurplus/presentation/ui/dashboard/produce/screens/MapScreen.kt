package com.example.sharingsurplus.presentation.ui.dashboard.produce.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels.MapViewModel
import com.google.android.gms.maps.model.LatLng

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = hiltViewModel(),
    navController: NavHostController?
    ) {

    val uiState by mapViewModel.mapUiState.collectAsState()

    ScaffoldComponent(
        modifier = modifier,
        topBar = { TopAppBarWithBackComponent(title = "Pick Up Location",onBackClick = {navController?.navigateUp()})},
        content = {
            MapScreenMinor(
                location = LatLng(uiState.produceLatitude,uiState.produceLongitude),
                snippet = uiState.produceLocation,
                zoom = uiState.produceZoomState
            )
        }
    )
}