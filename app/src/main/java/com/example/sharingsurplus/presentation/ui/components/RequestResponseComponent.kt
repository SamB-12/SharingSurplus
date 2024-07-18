package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.AccentColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor

@Composable
fun RequestResponseComponent(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
    title: String = "",
    message: String = "",
    reason:String = "",
    onReasonChange: (String) -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    //.padding(16.dp),
                //horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = message, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                TextFieldComponent(
                    label = "Enter the reason to reject.",
                    value = reason,
                    onValueChanged = onReasonChange
                )
            }
            },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Confirm", color = SecondaryColor)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = "Cancel", color = AccentColor)
            }
        },
        shape = RoundedCornerShape(8.dp),
        containerColor = PrimaryColor,
        tonalElevation = 2.dp
    )
}

@Preview
@Composable
private fun OnRequestRejectComponentPreview() {
    RequestResponseComponent(title = "Title", message = "Message")
}