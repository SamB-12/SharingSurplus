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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.states.dashboard.profile.ProfileScreenUiState
import com.example.sharingsurplus.presentation.navigation.Routes
import com.example.sharingsurplus.presentation.ui.components.ButtonComponent
import com.example.sharingsurplus.presentation.ui.components.ProfilePageUnitComponent
import com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels.ProfileScreenViewModel
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun ProfileMenuScreen(
    modifier: Modifier = Modifier,
    profileScreenViewModel: ProfileScreenViewModel = hiltViewModel(),
    navController: NavController?,
    ) {

    //val uiState = ProfileScreenUiState("Phil")

    val uiState by profileScreenViewModel.profileUiState.collectAsState()

    if (uiState.isLoggedOut){
        LaunchedEffect(Unit) {
            navController?.navigate(Routes.Login.route){
                popUpTo(Routes.Profile.route){
                    inclusive = true
                }
            }
        }
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(40.dp))
        Icon(imageVector = Icons.Rounded.Person, contentDescription = "Person Icon", modifier = modifier.size(180.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = uiState.name, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(32.dp))
        //Text(text = "View your profile", style = MaterialTheme.typography.bodyMedium, color = PrimaryTextColor)
        ProfilePageUnitComponent(text = "View your profile", icon = Icons.Rounded.AccountCircle, onClick = {navController?.navigate(Routes.ProfileInfo.route)})
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfilePageUnitComponent(text = "Edit your profile", icon = Icons.Filled.Edit)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfilePageUnitComponent(text = "View your Good Deed points", icon = ImageVector.vectorResource(
            id = R.drawable.ic_currency_rupee_24
        ))
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfilePageUnitComponent(text = "Change your password", icon = ImageVector.vectorResource(id = R.drawable.ic_password_reset_24))
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfilePageUnitComponent(text = "About us", icon = Icons.Filled.Info)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(40.dp))
        ButtonComponent(onClick = { profileScreenViewModel.logout() }, text = "Log Out")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "\u00A9 2024. Sharing Surplus. All rights reserved.", style = MaterialTheme.typography.bodySmall, color = PrimaryTextColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileMenuScreen(navController = null)
}