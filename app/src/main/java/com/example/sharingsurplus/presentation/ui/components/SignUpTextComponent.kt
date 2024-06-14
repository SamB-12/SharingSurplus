package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.AccentColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryTextColor

@Composable
fun SignUpTextComponent(modifier: Modifier = Modifier, text1: String, text2: String, onSignUpClicked: () -> Unit = {}) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = text1, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = SecondaryTextColor)
        Spacer(modifier = modifier.width(8.dp))
        Text(text = text2, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = AccentColor, modifier = modifier.clickable { onSignUpClicked() })
    }
}

@Preview
@Composable
private fun SignUpTextComponentPreview() {
    SignUpTextComponent(text1 = "Don't have an account?", text2 = "Sign Up")
}