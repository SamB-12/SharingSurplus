package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.titleFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "Sharing Surplus"
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.displayLarge.copy(fontFamily = titleFontFamily))},
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = PrimaryColor
        )
    )
}


@Preview
@Composable
private fun TitleTopAppBarComponentPreview() {
    TitleTopAppBar(title = "Sharing Surplus")
}