package com.example.sharingsurplus

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.sharingsurplus.presentation.ui.auth.screens.LoginScreen
import com.example.sharingsurplus.presentation.ui.theme.SharingSurplusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharingSurplusTheme {
//                Scaffold(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.primary)) { innerPadding ->
////                    Greeting(
////                        name = "Android",
////                        modifier = Modifier.padding(innerPadding)
////                    )
//
//                }

                LoginScreen(modifier = Modifier, painter = painterResource(id = R.drawable.ic_sharing_surplus_logo), onButtonClicked = { context ->
                    Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SharingSurplusTheme {
        Greeting("Android")
    }
}