package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@Composable
fun MultipleOptionPickerDialogComponent(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    option1: String = "Select photo from Gallery",
    option2: String = "Capture photo from Camera",
    onOption1Click: () -> Unit = {},
    onOption2Click: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Select Action") },
//        text = {
//            Column {
//                Text(text = "Choose an action to select or capture an image.")
//            }
//        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DialogOptionComponent(option = option1, onOptionClick = onOption1Click)
                DialogOptionComponent(option = option2, onOptionClick = onOption2Click)
            }
        },
        shape = RoundedCornerShape(8.dp),
        containerColor = PrimaryColor,
        tonalElevation = 2.dp
    )
}

@Preview
@Composable
private fun MultipleOptionPickerDialogComponentPreview() {
    MultipleOptionPickerDialogComponent()
}