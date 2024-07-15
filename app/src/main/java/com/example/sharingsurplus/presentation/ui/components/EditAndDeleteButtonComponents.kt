package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.AccentColor

@Composable
fun EditAndDeleteButtonComponents(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        //.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.weight(1f)){
            ButtonComponent(onClick = onEditClick, text = "Edit")
        }
        Box(modifier = Modifier.weight(1f)){
            ButtonComponent(onClick = onDeleteClick, text = "Delete", color = ButtonDefaults.buttonColors(AccentColor))
        }
    }
}

@Preview
@Composable
private fun EditAndDeleteButtonComponentsPreview() {
    EditAndDeleteButtonComponents()
}