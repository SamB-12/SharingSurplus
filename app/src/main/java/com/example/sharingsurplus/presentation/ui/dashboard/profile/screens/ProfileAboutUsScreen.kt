package com.example.sharingsurplus.presentation.ui.dashboard.profile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun ProfileAboutUsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?
) {
    ScaffoldComponent(
        modifier = modifier,
        topBar = { TopAppBarWithBackComponent(title = "About Us", onBackClick = {navController?.navigateUp()})},
        content = { ProfileAboutUsScreenMinor(painter = painterResource(id = R.drawable.ic_sharing_surplus_logo))}
    )
}

@Preview
@Composable
private fun ProfileAboutUsScreenPreview() {
    ProfileAboutUsScreen(navController = null)
}