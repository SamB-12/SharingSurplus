package com.example.sharingsurplus.presentation.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.auth.screens.RegistrationScreen
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.SharingSurplusTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldComponent(
    modifier: Modifier,
    topBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var pad: PaddingValues ? = null
    Scaffold(
        modifier = modifier.background(color = PrimaryColor),
        content = {padding ->
            Column {
                topBar()
                content()
            }
            //content()
            pad = padding
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun ScaffoldComponentPreview() {
    SharingSurplusTheme {
        ScaffoldComponent(modifier = Modifier, topBar = {TopAppBarRegistrationComponent(title = "Register")}, content = { RegistrationScreen(
            painter = painterResource(id = R.drawable.ic_sharing_surplus_logo)
        )})
    }
}