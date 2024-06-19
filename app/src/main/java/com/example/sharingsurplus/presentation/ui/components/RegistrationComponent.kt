package com.example.sharingsurplus.presentation.ui.components

import PasswordFieldComponent
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.presentation.navigation.Routes
import com.example.sharingsurplus.presentation.ui.auth.viewmodel.RegisterViewModel
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun RegistrationComponent(
    modifier: Modifier = Modifier,
    painter: Painter,
    registerViewModel : RegisterViewModel,
    navController: NavController ?= null,
) {

    val context = LocalContext.current
    val uiState by registerViewModel.registerUiState.collectAsState()

    LaunchedEffect(uiState.authResult) {
        when (uiState.authResult) {
            is AuthResult.Success -> {
                Toast.makeText(context, "Successfully registered! please log in!", Toast.LENGTH_SHORT).show()
                navController?.navigate(Routes.Home.route){
                    popUpTo(Routes.Register.route){
                        inclusive = true
                    }
                }
            }
            is AuthResult.Error -> {
                Toast.makeText(context, (uiState.authResult as AuthResult.Error).message?: "Unknown Error", Toast.LENGTH_SHORT).show()
                registerViewModel.resetAuthResult()
            }
            else -> {
                //Nothing
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painter, contentDescription = "Logo", modifier = modifier.padding(top = 12.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Create an account to give to the community", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Name", value = uiState.name, onValueChanged = { registerViewModel.onNameChanged(it)})
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Email", value = uiState.email, onValueChanged = { registerViewModel.onEmailChanged(it)})
        Spacer(modifier = Modifier.height(16.dp))
        PasswordFieldComponent(label = "Password", value = uiState.password, onValueChanged = { registerViewModel.onPasswordChanged(it)})
        Spacer(modifier = Modifier.height(16.dp))
        PasswordFieldComponent(label = "Confirm Password", value = uiState.confirmPassword, onValueChanged = { registerViewModel.onConfirmPasswordChanged(it)})
        Spacer(modifier = Modifier.height(16.dp))
        TermsAndConditionsComponent(isChecked = uiState.isChecked, onCheckedChange = { registerViewModel.onCheckChanged(it)})
        Spacer(modifier = Modifier.height(16.dp))
        ButtonComponent(onClick = { registerViewModel.register() }, text = "Register")
        Spacer(modifier = Modifier.height(16.dp))
        SignTextComponent(text1 = "Already have an account?", text2 = "Log In", onSignClicked = { navController?.navigateUp() })

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }
}