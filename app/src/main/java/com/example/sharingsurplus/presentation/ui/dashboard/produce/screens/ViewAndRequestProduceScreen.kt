package com.example.sharingsurplus.presentation.ui.dashboard.produce.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels.ViewAndRequestViewModel

@Composable
fun ViewAndRequestProduceScreen(
    modifier: Modifier = Modifier,
    viewAndRequestViewModel: ViewAndRequestViewModel = hiltViewModel(),
    navController: NavHostController?
) {

    val localContext = LocalContext.current

    val uiState by viewAndRequestViewModel.viewAndRequestUiState.collectAsState()

    ScaffoldComponent(
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = "Request Produce", onBackClick = {navController?.navigateUp()})},
        content = {
            ViewAndRequestProduceScreenMinor(
                painter = rememberAsyncImagePainter(model = uiState.imageUrl),
                produceName = uiState.produceName,
                produceDescription = uiState.produceDescription,
                produceType = uiState.produceType,
                produceQuantity = uiState.produceQuantity,
                produceUnit = uiState.produceUnit,
                producePickupInstructions = uiState.producePickupInstructions,
                produceBestBeforeDate = uiState.produceBestBeforeDate,
                producerName = uiState.producerName,
                produceLocation = uiState.produceLocation,
                pickupDate = uiState.producePickUpDate,
                pickupTime = uiState.producePickupTime,
                requirements = uiState.producePickUpRequirements,
                isDatePickerVisible = uiState.isDatePickerVisible,
                isTimePickerVisible = uiState.isTimePickerVisible,
                onPickUpDateSelected = {viewAndRequestViewModel.onPickUpDateChange(it)},
                onPickUpTimeSelected = {viewAndRequestViewModel.onPickUpTimeChange(it)},
                onRequirementsChanged = {viewAndRequestViewModel.onPickUpRequirementsChange(it)},
                onDatePickerVisible = {viewAndRequestViewModel.onDatePickerDialogChange(it)},
                onTimePickerVisible = {viewAndRequestViewModel.onTimePickerDialogChange(it)},
                onProduceRequested = {Toast.makeText(localContext, "Item Requested", Toast.LENGTH_SHORT).show()}
            )
        }
    )
}