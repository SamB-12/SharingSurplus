package com.example.sharingsurplus.presentation.ui.dashboard.requests.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.components.ConfirmationDialogComponent
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.requests.viewmodels.RequestEditViewModel

@Composable
fun RequestEditScreen(
    modifier: Modifier = Modifier,
    requestEditViewModel: RequestEditViewModel = hiltViewModel(),
    navController: NavHostController?
) {

    val localContext = LocalContext.current

    val uiState by requestEditViewModel.editRequestUiState.collectAsState()

    LaunchedEffect(uiState.requestEditResult) {
        when (uiState.requestEditResult) {
            is Result.Success -> {
                Toast.makeText(localContext, "Request Edited Successful", Toast.LENGTH_SHORT).show()
                navController?.navigateUp()
            }
            is Result.Error -> {
                Toast.makeText(localContext, (uiState.requestEditResult as Result.Error).message?:"unknown error", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //Nothing
            }
        }
    }

    if (uiState.isConfirmDialogVisible) {
        ConfirmationDialogComponent(
            title = "Edit Request?",
            message = "Are you sure you want to edit this Request?",
            onConfirm = {
                requestEditViewModel.onConfirmDialogVisibleChange(false)
                requestEditViewModel.editRequest()
            },
            onCancel = {
                requestEditViewModel.onConfirmDialogVisibleChange(false)
            }
        )
    }

    ScaffoldComponent(
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = "Edit Request", onBackClick = {navController?.navigateUp()}) },
        content = {
            RequestEditScreenMinor(
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
                onPickUpDateSelected = {requestEditViewModel.onPickUpDateChange(it)},
                onPickUpTimeSelected = {requestEditViewModel.onPickUpTimeChange(it)},
                onRequirementsChanged = {requestEditViewModel.onPickUpRequirementsChange(it)},
                onDatePickerVisible = {requestEditViewModel.onDatePickerDialogChange(it)},
                onTimePickerVisible = {requestEditViewModel.onTimePickerDialogChange(it)},
                onRequestedQuantityChanged = {requestEditViewModel.onRequestedQuantityChange(it)},
                onProduceLocationClicked = { navController?.navigate(Routes.ViewMap.route) },
                onRequestEdit = {
                    requestEditViewModel.onConfirmDialogVisibleChange(true)
                }
            )
        }
    )
}

@Preview
@Composable
private fun RequestEditScreenPreview() {
    RequestEditScreen(navController = null)
}