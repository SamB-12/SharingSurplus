package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.AccentColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun UrlOpenerComponent(
    modifier: Modifier = Modifier,
    text1: String = "",
    text2: String = "",
    onUrlClicked: () -> Unit = {}
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
        //horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$text1:",modifier = modifier.weight(1f), style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor)
        Text(text = text2, modifier= modifier.weight(2f).clickable {
            onUrlClicked()
        }, style = MaterialTheme.typography.bodyLarge, color = AccentColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun UrlOpenerComponentPreview() {
    UrlOpenerComponent(text1 = "Name", text2 = "Phil K")
}