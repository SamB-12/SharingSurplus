package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.AccentColor

@Composable
fun FloatingActionButtonComponent(modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.BottomCenter).offset(y = 32.dp),
            containerColor = AccentColor
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@Preview
@Composable
private fun FloatingActionButtonComponentPreview() {
    FloatingActionButtonComponent()
}