package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuantitySelectorComponent(
    modifier: Modifier = Modifier,
    label1: String = "Quantity",
    label2: String = "Unit",
    quantity: String = "",
    unit: String = "",
    onQuantityChange: (String) -> Unit = {},
    onUnitChange: (String) -> Unit = {},
    readOnly: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
            //.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.weight(1f)){
            TextFieldComponent(label = label1, value = quantity, onValueChanged = onQuantityChange, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))
        }
        Box(modifier = Modifier.weight(1f)){
            TextFieldComponent(label = label2, value = unit, onValueChanged = onUnitChange, readOnly = readOnly)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuantitySelectorComponentPreview() {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        QuantitySelectorComponent()
    }

}