package com.example.sharingsurplus.presentation.ui.dashboard.profile.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels.MyProduceViewModel

@Composable
fun MyProduceScreen(
    modifier: Modifier = Modifier,
    myProduceViewModel: MyProduceViewModel = hiltViewModel(),
    navController: NavHostController?
) {

    val uiState by myProduceViewModel.myProduceUiState.collectAsState()

    ScaffoldComponent(
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = "My Produce", onBackClick = {navController?.navigateUp()})},
        content = {
            MyProduceScreenMinor(produceList = uiState.produceList, onItemClick = {navController?.navigate(Routes.EditAndDeleteProduce.route)})
        }
    )
}

@Preview
@Composable
private fun MyProduceScreenPreview() {
    MyProduceScreen(navController = null)
}