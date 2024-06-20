package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun ProfilePageUnitComponent(
    modifier: Modifier = Modifier,
    text:String = "hi",
    icon:ImageVector = Icons.Filled.AccountCircle,
    onClick:() -> Unit = {}
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row (modifier = Modifier.weight(1f)) {
            Icon(imageVector = icon, contentDescription = "Icon")
            Text(
                text = text,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "rightArrow")
    }
}


@Preview(showBackground = true)
@Composable
private fun ProfilePageUnitComponentPreview() {
    ProfilePageUnitComponent(text = "View my profile")
}