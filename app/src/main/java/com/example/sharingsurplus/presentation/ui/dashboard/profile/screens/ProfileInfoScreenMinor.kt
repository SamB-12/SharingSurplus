package com.example.sharingsurplus.presentation.ui.dashboard.profile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.data.states.dashboard.profile.ProfileInfoScreenUiState
import com.example.sharingsurplus.presentation.ui.components.ProfileInfoDetailsComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun ProfileInfoScreenMinor(
    modifier: Modifier = Modifier,
    name: String = "Phil Kolling",
    email: String = "phil@test.ac.uk",
    phone: String = "07987654321",
    address: String = "123 Test Street",
    dateJoined: String = "2023-01-01"
    ) {
    val uiState = ProfileInfoScreenUiState("Phil Kolling", "phil@test.ac.uk")

    //val uiState by profileInfoScreenViewModel.profileInfoUiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Icon(imageVector = Icons.Rounded.Person, contentDescription = "Person Icon", modifier = modifier.size(180.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = name, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(32.dp))
        //Text(text = "View your profile", style = MaterialTheme.typography.bodyMedium, color = PrimaryTextColor)
        ProfileInfoDetailsComponent(text1 = "Name", text2 = name)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        ProfileInfoDetailsComponent(text1 = "Email", text2 = email)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        ProfileInfoDetailsComponent(text1 = "Phone", text2 = phone)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        ProfileInfoDetailsComponent(text1 = "Address", text2 = address)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        ProfileInfoDetailsComponent(text1 = "Joined", text2 = dateJoined)
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
private fun ProfileInfoScreenMinorPreview() {
    ProfileInfoScreenMinor()
}