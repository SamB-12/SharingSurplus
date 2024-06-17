package com.example.sharingsurplus.presentation.ui.auth.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.components.RegistrationComponent
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarRegistrationComponent

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier, painter: Painter, onBackClicked: () -> Unit = {}, onSignInClicked: () -> Unit = {}) {
    ScaffoldComponent(
        modifier = modifier,
        topBar = {TopAppBarRegistrationComponent(title = "Registration", onBackClick = onBackClicked)},
        content = { RegistrationComponent(painter = painter,onSignInClicked = onSignInClicked)}
    )
}

@Preview(showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreen(painter = painterResource(id = R.drawable.ic_sharing_surplus_logo))
}