package com.example.sharingsurplus.presentation.ui.dashboard.profile.screens

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.states.dashboard.profile.ProfileKarmaPointsScreenUiState
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun ProfileKarmaPointsScreenMinor(
    modifier: Modifier = Modifier,
    karmaPoints: Int = 5
) {

    //val uiState = ProfileKarmaPointsScreenUiState(5)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Icon(imageVector = ImageVector.vectorResource(
            id = R.drawable.ic_currency_rupee_24
        ), contentDescription = "Karma Icon", modifier = modifier.size(180.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Good Deed Points", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "You have ${karmaPoints} Good Deed points. Keep participating more to earn more points", modifier = modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.bodyMedium, color = PrimaryTextColor)
//        Spacer(modifier = Modifier
//            .padding(horizontal = 16.dp)
//            .height(1.dp)
//            .background(color = Color.Gray)
//            .fillMaxWidth()
//        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileKarmaPointsScreenMinorPreview() {
    ProfileKarmaPointsScreenMinor()
}