package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
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
import com.example.sharingsurplus.presentation.ui.theme.SecondaryTextColor

@Composable
fun ConfirmationDialogComponent(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
    title: String = "",
    message: String = ""
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
        text = { Text(text = message, style = MaterialTheme.typography.bodyLarge) },
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

@Preview(showBackground = true)
@Composable
private fun ConfirmationDialogComponentPreview() {
    ConfirmationDialogComponent(title = "Title", message = "Message", onConfirm = {}, onCancel = {})
}

