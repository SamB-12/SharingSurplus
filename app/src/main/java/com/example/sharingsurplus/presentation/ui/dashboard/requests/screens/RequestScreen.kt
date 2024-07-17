package com.example.sharingsurplus.presentation.ui.dashboard.requests.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.components.ProfilePageUnitComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun RequestScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    paddingValues: PaddingValues = PaddingValues()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_requests_24),
            contentDescription = "request_icon",
            modifier = modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Requests", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(32.dp))
        ProfilePageUnitComponent(text = "Produce Requests Received", icon = ImageVector.vectorResource(id = R.drawable.ic_request_recieved_24), onClick = {
            navController?.navigate(Routes.RequestReceived.route)
        })
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfilePageUnitComponent(text = "Produce Requests Sent", icon = ImageVector.vectorResource(id = R.drawable.ic_request_sent_24), onClick = {
            navController?.navigate(Routes.RequestSent.route)
        })
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfilePageUnitComponent(text = "Status/Notification", icon = Icons.Rounded.Notifications, onClick = {})
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun RequestScreenPreview() {
    RequestScreen(navController = null)
}