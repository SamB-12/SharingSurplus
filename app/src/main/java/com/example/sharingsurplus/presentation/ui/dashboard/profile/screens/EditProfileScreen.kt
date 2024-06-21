package com.example.sharingsurplus.presentation.ui.dashboard.profile.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.states.dashboard.profile.EditProfileScreenUiState
import com.example.sharingsurplus.presentation.navigation.Routes
import com.example.sharingsurplus.presentation.ui.components.ButtonComponent
import com.example.sharingsurplus.presentation.ui.components.ConfirmationDialogComponent
import com.example.sharingsurplus.presentation.ui.components.SignTextComponent
import com.example.sharingsurplus.presentation.ui.components.TermsAndConditionsComponent
import com.example.sharingsurplus.presentation.ui.components.TextFieldComponent
import com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels.EditProfileViewModel
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    editProfileViewModel: EditProfileViewModel = hiltViewModel(),
    navController: NavController?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        //val uiState = EditProfileScreenUiState("Phil K", "philk@gmail.com", "123456", "address")
        val uiState by editProfileViewModel.editProfileUiState.collectAsState()
        val context = LocalContext.current
        val painter: Painter = painterResource(id = R.drawable.ic_sharing_surplus_logo)

        Image(painter = painter, contentDescription = "Logo", modifier = modifier.padding(top = 12.dp))//change it to have a edit icon and make it clickable
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Edit your profile details", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Name", value = uiState.name, onValueChanged = { editProfileViewModel.onNameChanged(it) })
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Email", value = uiState.email, onValueChanged = { editProfileViewModel.onEmailChanged(it) })
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Phone", value = uiState.phone, onValueChanged = { editProfileViewModel.onPhoneChanged(it) })
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Address", value = uiState.address, onValueChanged = { editProfileViewModel.onAddressChanged(it) })
        Spacer(modifier = Modifier.height(40.dp))
        ButtonComponent(onClick = { editProfileViewModel.showDialog() }, text = "Confirm details") //add a alert dialog when pressed
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isAlertDialogVisible){
            ConfirmationDialogComponent(
                title = "Confirm Changes?",
                message = "Are you sure you want to save the changes?",
                onConfirm = {editProfileViewModel.updateProfile()},
                onCancel = {editProfileViewModel.hideDialog()}
            )
        }

        LaunchedEffect(uiState.isSuccess) {
            when (uiState.isSuccess) {

                is AuthResult.Success -> {
                    Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                    navController?.navigate(Routes.Profile.route){
                        popUpTo(Routes.Profile.route){
                            inclusive = true
                        }
                    }
                }
                is AuthResult.Error -> {
                    Toast.makeText(context, (uiState.isSuccess as AuthResult.Error).message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //Nothing
                }
            }
        }

        if (uiState.isLoading){
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    EditProfileScreen(navController = null)
}