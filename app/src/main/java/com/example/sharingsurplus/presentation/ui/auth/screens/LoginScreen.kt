package com.example.sharingsurplus.presentation.ui.auth.screens

import PasswordFieldComponent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.example.sharingsurplus.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.presentation.navigation.Routes
import com.example.sharingsurplus.presentation.ui.auth.viewmodel.LoginViewModel
import com.example.sharingsurplus.presentation.ui.components.ButtonComponent
import com.example.sharingsurplus.presentation.ui.components.ForgotPasswordTextComponent
import com.example.sharingsurplus.presentation.ui.components.SignTextComponent
import com.example.sharingsurplus.presentation.ui.components.TextFieldComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    painter: Painter,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController?,
    onForgotPassword: () -> Unit = {}) {

    val context = LocalContext.current
    val uiState by loginViewModel.loginUiState.collectAsState()


    LaunchedEffect(uiState.authResult) {
        when (uiState.authResult) {
            is AuthResult.Success -> {
                navController?.navigate(Routes.Profile.route){
                    popUpTo(Routes.Login.route){
                        inclusive = true
                    }
                }
            }
            is AuthResult.Error -> {
                Toast.makeText(context, (uiState.authResult as AuthResult.Error).message?: "Unknown Error", Toast.LENGTH_SHORT).show()
                loginViewModel.resetAuthResult()
            }
            else -> {
                //Nothing
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            //.padding(16.dp)
            .background(color = PrimaryColor),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ){
        Spacer(modifier = Modifier.height(40.dp))
        Image(painter = painter, contentDescription = "content Logo", alignment = Alignment.TopCenter, modifier = modifier.size(240.dp))
        Text(text = "Login", style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor, modifier = Modifier.padding(bottom = 16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Email", value = uiState.email, onValueChanged = {loginViewModel.onEmailChanged(it)})
        Spacer(modifier = Modifier.height(16.dp))
        //TextFieldComponent(label = "password", value = uiState.password, onValueChanged = {loginViewModel.onPasswordChanged(it)})
        PasswordFieldComponent(label = "Password", value = uiState.password, onValueChanged = {loginViewModel.onPasswordChanged(it)})
        Spacer(modifier = Modifier.height(16.dp))
        ForgotPasswordTextComponent(text = "Forgot Password?", modifier = modifier, onForgotPasswordClicked = { onForgotPassword})
        Spacer(modifier = Modifier.height(24.dp))
        ButtonComponent(onClick = { loginViewModel.login()  }, text = "Login")
        Spacer(modifier = Modifier.height(16.dp))
        SignTextComponent(text1 = "Don't have an account?", text2 = "Sign Up", modifier = modifier, onSignClicked = { navController?.navigate(Routes.Register.route)})

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(painter = painterResource(id = R.drawable.ic_sharing_surplus_logo), navController = null)
}