package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    onClick:() -> Unit, text:String,
    color:ButtonColors = ButtonDefaults.buttonColors(SecondaryColor)
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        //colors = ButtonDefaults.buttonColors(SecondaryColor)
        colors = color
    ) {
        Text(text = text, modifier = Modifier.padding(vertical = 8.dp), style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
private fun ButtonComponentPreview() {
    ButtonComponent(onClick = {}, text = "Login")
}