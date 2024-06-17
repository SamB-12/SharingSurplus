package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun RegistrationComponent(
    modifier: Modifier = Modifier,
    painter: Painter,
    onSignInClicked: () -> Unit
) {
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
        TextFieldComponent(label = "Name")
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Email")
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Password")
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Confirm Password")
        Spacer(modifier = Modifier.height(16.dp))
        TermsAndConditionsComponent(isChecked = false)
        Spacer(modifier = Modifier.height(16.dp))
        ButtonComponent(onClick = { }, text = "Register")
        Spacer(modifier = Modifier.height(16.dp))
        SignTextComponent(text1 = "Already have an account?", text2 = "Log In", onSignClicked = onSignInClicked)
    }
}