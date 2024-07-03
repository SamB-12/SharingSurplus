package com.example.sharingsurplus.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor

@Composable
fun LocationSelectorTextFieldComponent(
    modifier: Modifier = Modifier,
    label: String = "Location",
    location: String = "",
    onLocationChanged: (String) -> Unit = {},
    onLocationDialogVisibleChanged: (Boolean) -> Unit = {},
    isLocationDialogVisible: Boolean = false
) {
    OutlinedTextField(
        value = location,
        onValueChange = { text ->
            onLocationChanged(text)
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = PrimaryColor)
            .clickable {
                Log.i("This was clicked?", "YES INDEED")
                if (isLocationDialogVisible) {
                    onLocationDialogVisibleChanged(false)
                } else {
                    onLocationDialogVisibleChanged(true)
                }
            }
        ,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SecondaryColor,
            focusedLabelColor = SecondaryColor,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            disabledBorderColor = Color.Gray,
            disabledTextColor = Color.Black
        ),
        readOnly = true,
        singleLine = true,
        trailingIcon = {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Calendar")
        },
        enabled = false
    )

    if (isLocationDialogVisible) {
        MultipleOptionPickerDialogComponent(
            option1 = "Choose anonymous location",
            option2 = "Choose your location",
            onOption1Click = {},
            onOption2Click = {},
            onDismissRequest = {onLocationDialogVisibleChanged(false)}
        )
    }
}

@Preview
@Composable
private fun LocationSelectorTextFieldComponentPreview() {
    LocationSelectorTextFieldComponent()
}