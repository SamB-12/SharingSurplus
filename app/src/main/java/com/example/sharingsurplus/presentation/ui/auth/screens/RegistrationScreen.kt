package com.example.sharingsurplus.presentation.ui.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.components.ButtonComponent
import com.example.sharingsurplus.presentation.ui.components.SignUpTextComponent
import com.example.sharingsurplus.presentation.ui.components.TermsAndConditionsComponent
import com.example.sharingsurplus.presentation.ui.components.TextFieldComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier, painter: Painter) {
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
        ButtonComponent(onClick = { /*TODO*/ }, text = "Register")
        Spacer(modifier = Modifier.height(16.dp))
        SignUpTextComponent(text1 = "Already have an account?", text2 = "Log In")
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreen(painter = painterResource(id = R.drawable.ic_sharing_surplus_logo))
}