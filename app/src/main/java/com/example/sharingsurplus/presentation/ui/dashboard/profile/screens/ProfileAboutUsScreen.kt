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
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun ProfileAboutUsScreen(modifier: Modifier = Modifier,painter:Painter) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Image(painter = painter, contentDescription = "Logo", modifier = modifier.size(180.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "About Sharing Surplus", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Version 1.0", modifier = modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.bodyLarge, color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "At Sharing Surplus, we aim to reduce food waste by connecting surplus food with those in need.", modifier = modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.bodyLarge, color = PrimaryTextColor, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Developed as part of MSc Dissertation project", modifier = modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.bodyLarge, color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Author: 230016680", modifier = modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.bodyLarge, color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Contact: smb38@st-andrews.ac.uk", modifier = modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.bodyLarge, color = PrimaryTextColor)
    }
}

@Preview
@Composable
private fun ProfileAboutUsScreenPreview() {
    ProfileAboutUsScreen(painter = painterResource(id = R.drawable.ic_sharing_surplus_logo))
}