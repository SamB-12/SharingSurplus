package com.example.sharingsurplus

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.sharingsurplus.presentation.navigation.SampleNavGraph
import com.example.sharingsurplus.presentation.ui.dashboard.main_menu.screens.MainMenuScreen
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
            //val navController = rememberNavController()
            val isMainMenu by mainViewModel.isMainMenu.observeAsState(false)

            Log.d("MainActivity", "MainState: $mainState")

            SharingSurplusTheme {

//                LaunchedEffect(mainState.isNavHostMain){
//                    when (mainState.isNavHostMain){
//                        true -> {
//                            MainMenuScreen()
//                        }
//                        false -> {
//                            SampleNavGraph()
//                    }
//                }

//                if (mainState.isNavHostMain){
//                    MainMenuScreen()
//                } else{
//                    SampleNavGraph()
//                }

//                when (mainState.isNavHostMain){
//                    true -> {
//                        MainMenuScreen()
//                        Log.i("Check3","It never worked!")
//                    }
//                    false -> SampleNavGraph()
//                }



                SampleNavGraph(startDestination = mainState.route)

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