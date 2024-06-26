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
import androidx.compose.material.icons.rounded.AccountCircle
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sharingsurplus.data.states.dashboard.profile.ProfileInfoScreenUiState
import com.example.sharingsurplus.presentation.ui.components.ProfileInfoDetailsComponent
import com.example.sharingsurplus.presentation.ui.components.ProfilePageUnitComponent
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels.ProfileInfoScreenViewModel
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun ProfileInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    profileInfoScreenViewModel : ProfileInfoScreenViewModel = hiltViewModel()
) {

    //val uiState = ProfileInfoScreenUiState("Phil Kolling", "phil@test.ac.uk")

    val uiState by profileInfoScreenViewModel.profileInfoUiState.collectAsState()

    ScaffoldComponent(
        modifier = modifier,
        topBar = { TopAppBarWithBackComponent(title = "Profile Info", onBackClick = {navController?.navigateUp()})},
        content = { ProfileInfoScreenMinor(name = uiState.name, email = uiState.email, phone = uiState.phone, address = uiState.address, dateJoined = uiState.dateJoined)}
    )

}

@Preview(showBackground = true)
@Composable
private fun ProfileInfoScreenPreview() {
    ProfileInfoScreen(navController = null)
}