package com.example.sharingsurplus.presentation.ui.dashboard.produce.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels.AddProduceViewModel

@Composable
fun AddProduceScreen(
    modifier: Modifier = Modifier,
    addProduceViewModel: AddProduceViewModel = hiltViewModel(),
    navController: NavHostController?
) {

    val uiState by addProduceViewModel.addProduceUiState.collectAsState()

    val localContext = LocalContext.current

    ScaffoldComponent(//integrate the vm
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = "Add a produce", onBackClick = {navController?.navigateUp()})},
        content = {
            AddScreenMinor(
                produceName = uiState.produceName,
                produceDescription = uiState.produceDescription,
                produceType = uiState.produceType!!,
                quantity = uiState.produceQuantity.toString(),
                unit = uiState.produceUnit,
                location = uiState.produceLocation,
                bestBeforeDate = uiState.produceBestBeforeDate,
                pickupInstructions = uiState.producePickupInstructions,
                isDatePickerDialogVisible = uiState.isDatePickerDialogVisible,
                onProduceNameChange = addProduceViewModel::onProduceNameChanged,
                onProduceDescriptionChange = addProduceViewModel::onProduceDescriptionChanged,
                onProduceTypeChange = addProduceViewModel::onProduceTypeChanged,
                onQuantityChange = addProduceViewModel::onProduceQuantityChanged,
                onUnitChange = addProduceViewModel::onProduceUnitChanged,
                onLocationChange = addProduceViewModel::onLocationChanged,
                onPickupInstructionsChange = addProduceViewModel::onProducePickupInstructionChanged,
                onBestBeforeDateChange = addProduceViewModel::onProduceBestBeforeDateChanged,
                onIsDatePickerDialogVisibleChange = addProduceViewModel::isDatePickerDialogVisible,
                isImagePickerDialogVisible = uiState.isAddImageDialogVisible,
                isLocationPickerDialogVisible = uiState.isLocationDialogVisible,
                onImagePickerDialogVisibleChange = addProduceViewModel::isImagePickerDialogVisible,
                onLocationPickerDialogVisibleChange = addProduceViewModel::isLocationPickerDialogVisible,
                onGalleryClick = {Toast.makeText(localContext, "Gallery Clicked", Toast.LENGTH_SHORT).show()},
                onCameraClick = {Toast.makeText(localContext, "Camera Clicked", Toast.LENGTH_SHORT).show()}
            )
        }
    )
}

@Preview
@Composable
private fun AddProduceScreenPreview() {
    AddProduceScreen(navController = null)
}