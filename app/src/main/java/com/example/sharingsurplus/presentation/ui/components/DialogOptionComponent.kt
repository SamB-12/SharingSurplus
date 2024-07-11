package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryTextColor

@Composable
fun DialogOptionComponent(
    modifier: Modifier = Modifier,
    option: String,
    onOptionClick: () -> Unit = {}
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        TextButton(onClick = {onOptionClick()}) {
            Text(text = option, style = MaterialTheme.typography.bodyLarge, color = SecondaryTextColor)
        }
    }
}

@Preview
@Composable
private fun DialogOptionComponentPreview() {
    DialogOptionComponent(option = "Choose an image from gallery")
}