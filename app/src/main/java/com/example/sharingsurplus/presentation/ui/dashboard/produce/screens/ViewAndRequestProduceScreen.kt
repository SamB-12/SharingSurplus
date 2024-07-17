package com.example.sharingsurplus.presentation.ui.dashboard.produce.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.components.ConfirmationDialogComponent
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

    LaunchedEffect(uiState.requestResult) {
        when (uiState.requestResult) {
            is AuthResult.Success -> {
                Toast.makeText(localContext, "Request Successful", Toast.LENGTH_SHORT).show()
                navController?.navigateUp()
            }
            is AuthResult.Error -> {
                Toast.makeText(localContext, (uiState.requestResult as AuthResult.Error).message?:"unknown error", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //Nothing
            }
        }
    }

    if (uiState.isConfirmDialogVisible) {
        ConfirmationDialogComponent(
            title = "Request Produce?",
            message = "Are you sure you want to request this produce?",
            onConfirm = {
                viewAndRequestViewModel.onConfirmDialogVisibleChange(false)
                viewAndRequestViewModel.requestProduce()
            },
            onCancel = {
                viewAndRequestViewModel.onConfirmDialogVisibleChange(false)
            }
        )
    }

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
                requestedQuantity = uiState.requestedQuantity.toString(),
                requirements = uiState.producePickUpRequirements,
                isDatePickerVisible = uiState.isDatePickerVisible,
                isTimePickerVisible = uiState.isTimePickerVisible,
                onPickUpDateSelected = {viewAndRequestViewModel.onPickUpDateChange(it)},
                onPickUpTimeSelected = {viewAndRequestViewModel.onPickUpTimeChange(it)},
                onRequirementsChanged = {viewAndRequestViewModel.onPickUpRequirementsChange(it)},
                onDatePickerVisible = {viewAndRequestViewModel.onDatePickerDialogChange(it)},
                onTimePickerVisible = {viewAndRequestViewModel.onTimePickerDialogChange(it)},
                onRequestedQuantityChanged = {viewAndRequestViewModel.onRequestedQuantityChange(it)},
                onProduceLocationClicked = { navController?.navigate(Routes.ViewMap.route) },
                onProduceRequested = {
                    viewAndRequestViewModel.onConfirmDialogVisibleChange(true)
                }
            )
        }
    )
}