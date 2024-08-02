package com.example.sharingsurplus.presentation.ui.dashboard.produce.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.states.status.ProduceType
import com.example.sharingsurplus.presentation.ui.components.AddProduceImageComponent
import com.example.sharingsurplus.presentation.ui.components.ButtonComponent
import com.example.sharingsurplus.presentation.ui.components.CalendarSelectorTextFieldComponent
import com.example.sharingsurplus.presentation.ui.components.DropDownMenuComponent
import com.example.sharingsurplus.presentation.ui.components.LocationSelectorTextFieldComponent
import com.example.sharingsurplus.presentation.ui.components.QuantitySelectorComponent
import com.example.sharingsurplus.presentation.ui.components.TextFieldComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@Composable
fun AddScreenMinor(
    modifier: Modifier = Modifier,
    produceName: String = "",
    produceDescription: String = "",
    produceType: ProduceType = ProduceType.None,
    quantity: String = "",
    unit: String = "",
    location: String = "",
    pickupInstructions: String = "",
    bestBeforeDate: String = "",
    produceImage: Painter = painterResource(id = R.drawable.add_screen_image_placeholder),
    onProduceNameChange: (String) -> Unit = {},
    onProduceDescriptionChange: (String) -> Unit = {},
    onProduceTypeChange: (ProduceType) -> Unit = {},
    onQuantityChange: (String) -> Unit = {},
    onUnitChange: (String) -> Unit = {},
    onLocationChange: (String) -> Unit = {},
    onPickupInstructionsChange: (String) -> Unit = {},
    onBestBeforeDateChange: (String) -> Unit = {},
    isDatePickerDialogVisible: Boolean = false,
    onIsDatePickerDialogVisibleChange: (Boolean) -> Unit = {},
    onImageChange: (String) -> Unit = {},
    isImagePickerDialogVisible: Boolean = false,
    isLocationPickerDialogVisible: Boolean = false,
    onLocationPickerDialogVisibleChange: (Boolean) -> Unit = {},
    onImagePickerDialogVisibleChange: (Boolean) -> Unit = {},
    onGalleryClick: () -> Unit = {},
    onCameraClick: () -> Unit = {},
    onLocationPlacesClicked: () -> Unit = {},
    onCurrentLocationClicked: () -> Unit = {},
    onUploadButtonClicked: () -> Unit = {},
    isHomeLocationDialogVisible: Boolean = false,
    onIsHomeLocationDialogVisibleChange: (Boolean) -> Unit = {}
) {//add a view model as well
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = PrimaryColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Text(text = "Add a product", style = MaterialTheme.typography.headlineLarge)// replace with the top bar
        //Spacer(modifier = modifier.height(16.dp))
        Text(text = "Enter Produce Name", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        TextFieldComponent(label = "Product Name", value = produceName, onValueChanged = onProduceNameChange)
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Give a description", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        TextFieldComponent(label = "Product Description", value = produceDescription, onValueChanged = onProduceDescriptionChange)
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Select the type of produce", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        //TextFieldComponent(label = "Product Type")//This should be a dropdown/spinner
        DropDownMenuComponent(label = "Product Type", selectedType = produceType, produceTypes = ProduceType.entries, onTypeSelected = onProduceTypeChange)
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Enter the quanity of produce", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        QuantitySelectorComponent(label1 = "Quantity", label2 = "Unit", quantity = quantity, unit = unit, onQuantityChange = onQuantityChange, onUnitChange = onUnitChange) //label 2 should be a spinner
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Enter the pickup Instructions", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        TextFieldComponent(label = "Pickup Instructions", value = pickupInstructions, onValueChanged = onPickupInstructionsChange)
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Enter the Best Before pickup date", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        //TextFieldComponent(label = "BestBefore Date")//this should be a date picker!
        CalendarSelectorTextFieldComponent(
            onBestBeforeDateChanged = onBestBeforeDateChange,
            bestBeforeDate = bestBeforeDate,
            isDatePickerDialogVisible = isDatePickerDialogVisible,
            datePickerDialogChanged = onIsDatePickerDialogVisibleChange
        )
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Select the Location", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        //TextFieldComponent(label = "Location", value = location, onValueChanged = onLocationChange)//should ask the user for a normal location or an anonymous location
        LocationSelectorTextFieldComponent(
            label = "Location",
            location = location,
            onLocationChanged = onLocationChange,
            isLocationDialogVisible = isLocationPickerDialogVisible,
            onLocationDialogVisibleChanged = onLocationPickerDialogVisibleChange,
            onLocationPlacesClicked = onLocationPlacesClicked,
            onCurrentLocationClicked = onCurrentLocationClicked,
            isHomeLocationDialogVisible = isHomeLocationDialogVisible,
            onHomeLocationDialogVisibleChanged = { onIsHomeLocationDialogVisibleChange(it) }
        )
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Insert the Image", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        AddProduceImageComponent(
            painter = produceImage,
            isImagePickerDialogVisible = isImagePickerDialogVisible,
            onImagePickerDialogChange = onImagePickerDialogVisibleChange,
            onGalleryClicked = onGalleryClick,
            onCameraClicked = onCameraClick,
        )
        Spacer(modifier = modifier.height(16.dp))
        ButtonComponent(text = "Add Product", onClick = onUploadButtonClicked)
    }
}

@Preview(showBackground = true)
@Composable
private fun AddScreenMinorPreview() {
    AddScreenMinor()
}