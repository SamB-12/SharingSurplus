package com.example.sharingsurplus.presentation.ui.auth.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.sharingsurplus.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.components.ButtonComponent
import com.example.sharingsurplus.presentation.ui.components.ForgotPasswordTextComponent
import com.example.sharingsurplus.presentation.ui.components.SignUpTextComponent
import com.example.sharingsurplus.presentation.ui.components.TextFieldComponent
import com.example.sharingsurplus.presentation.ui.theme.AccentColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    painter: Painter,
    onButtonClicked: (context: android.content.Context) -> Unit = {},
    onSignUpClicked: () -> Unit = {},
    onForgotPassword: () -> Unit = {}) {

    val context = LocalContext.current

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
        TextFieldComponent(label = "email")
        //OutlinedTextField(value = "email", onValueChange = {}, modifier = Modifier.fillMaxWidth().padding(start = 16.dp,end=16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "password")
        //OutlinedTextField(value = "password", onValueChange = {}, modifier = Modifier.background(color = Color.White))
        Spacer(modifier = Modifier.height(16.dp))
        ForgotPasswordTextComponent(text = "Forgot Password?", modifier = modifier, onForgotPasswordClicked = { onForgotPassword})
        Spacer(modifier = Modifier.height(24.dp))
        ButtonComponent(onClick = { onButtonClicked(context) }, text = "Login")
        Spacer(modifier = Modifier.height(16.dp))
        SignUpTextComponent(text1 = "Don't have an account?", text2 = "Sign Up", modifier = modifier, onSignUpClicked = { onSignUpClicked })
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(painter = painterResource(id = R.drawable.ic_sharing_surplus_logo))
}