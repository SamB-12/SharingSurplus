package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.AccentColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor

@Composable
fun LocationSettingsDialogComponent(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    message: String
) {
    AlertDialog(
        title = { Text(text = title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
        text = { Text(text = message, style = MaterialTheme.typography.bodyLarge) },
        shape = RoundedCornerShape(8.dp),
        containerColor = PrimaryColor,
        tonalElevation = 2.dp,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Go to settings", color = SecondaryColor)
            }
        },
        onDismissRequest = onDismissRequest
    )
}