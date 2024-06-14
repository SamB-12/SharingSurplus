package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.AccentColor

@Composable
fun ForgotPasswordTextComponent(modifier: Modifier = Modifier, text:String, onForgotPasswordClicked: () -> Unit ={}) {
    Row (modifier = modifier
        .fillMaxWidth()
        .padding(end = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = AccentColor, textAlign = TextAlign.End, modifier = modifier.clickable { onForgotPasswordClicked() })
    }
}

@Preview
@Composable
private fun ForgotPasswordTextComponentPreview() {
    ForgotPasswordTextComponent(text = "Forgot password?")
}