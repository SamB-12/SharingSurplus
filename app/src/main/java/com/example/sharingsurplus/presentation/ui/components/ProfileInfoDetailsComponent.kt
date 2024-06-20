package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun ProfileInfoDetailsComponent(
    modifier: Modifier = Modifier,
    text1: String = "",
    text2: String = ""
    ) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        //horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$text1:", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor)
        Text(text = text2, modifier= modifier.padding(start = 16.dp), style = MaterialTheme.typography.bodyLarge, color = PrimaryTextColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileInfoDetailsComponentPreview() {
    ProfileInfoDetailsComponent(text1 = "Name", text2 = "Phil K")
}