package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuantitySelectorComponent(
    modifier: Modifier = Modifier,
    label1: String = "Quantity",
    label2: String = "Unit"
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
            //.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.weight(1f)){
            TextFieldComponent(label = label1)
        }
        Box(modifier = Modifier.weight(1f)){
            TextFieldComponent(label = label2)
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