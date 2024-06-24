package com.example.sharingsurplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.sharingsurplus.presentation.navigation.SampleNavGraph
import com.example.sharingsurplus.presentation.ui.theme.SharingSurplusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                mainViewModel.mainState.value.isLoading
            }

        }

        setContent {

            val mainState by mainViewModel.mainState.collectAsState()

            SharingSurplusTheme {

                SampleNavGraph(startDestination = mainState.route)

//                Scaffold(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.primary)) { innerPadding ->
////                    Greeting(
////                        name = "Android",
////                        modifier = Modifier.padding(innerPadding)
////                    )
//
//                }

//                LoginScreen(modifier = Modifier, painter = painterResource(id = R.drawable.ic_sharing_surplus_logo), onButtonClicked = { context ->
//                    Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
//                })
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