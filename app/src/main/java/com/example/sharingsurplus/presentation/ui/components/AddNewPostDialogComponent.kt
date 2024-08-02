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
fun AddNewPostDialogComponent(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
    title: String = "",
    message1: String = "",
    message2: String = "",
    postTitle: String = "",
    postContent: String = "",
    onPostTitleChange: (String) -> Unit = {},
    onPostContentChange: (String) -> Unit = {}
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
                Text(text = message1, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(8.dp))
                TextFieldComponent(
                    label = "Enter a title.",
                    value = postTitle,
                    onValueChanged = onPostTitleChange
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = message2, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(8.dp))
                TextFieldComponent(
                    label = "Enter the content of Post",
                    value = postContent,
                    onValueChanged = onPostContentChange
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

@Preview (showBackground = true)
@Composable
private fun AddNewPostDialogComponentPreview() {
    AddNewPostDialogComponent(title = "Add New Post", message1 = "Enter a title.", message2 = "Enter the content of Post")
}