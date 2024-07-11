package com.example.sharingsurplus.presentation.ui.dashboard.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.states.dashboard.home.HomeScreenUiState
import com.example.sharingsurplus.presentation.ui.components.ProduceItemCardComponent
import com.example.sharingsurplus.presentation.ui.dashboard.home.viewmodels.HomeScreenViewModel
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {

    val uiState by homeScreenViewModel.homeScreenUiState.collectAsState()
    //val uiState = HomeScreenUiState(name = "Phil K")

    val randomList = List(10){

    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor)
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        randomList.forEach {
//            item {
//                Spacer(modifier = Modifier.height(8.dp))
//                ProduceItemCardComponent(
//                    painter = painterResource(id = R.drawable.ic_andy),
//                    produceName = "Andy",
//                    produceType = "Vegetable",
//                    produceQuantity = "3",
//                    producerName = "Andreas Weisberg",
//                    produceDate = "2023-06-02"
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//        }

        if (uiState.produceList.isEmpty()){
            item {
                Text(text = "No produce found", color = PrimaryTextColor, textAlign = TextAlign.Center)
            }
        }

        uiState.produceList.forEach { produce ->
            item {
                Spacer(modifier = Modifier.height(8.dp))
                ProduceItemCardComponent(
                    painter = rememberAsyncImagePainter(
                        model = produce.produceImageUrl
                    ),
                    produceName = produce.produceName,
                    produceType = produce.produceType!!,
                    produceQuantity = produce.produceQuantity.toString(),
                    producerName = produce.producerName?:"Unknown Producer",
                    produceDate = produce.produceBestBeforeDate
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}