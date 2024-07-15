package com.example.sharingsurplus.presentation.ui.dashboard.profile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.sharingsurplus.data.model.Produce
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.components.ProduceItemCardComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor
import com.example.sharingsurplus.presentation.utils.GlobalVariables

@Composable
fun MyProduceScreenMinor(
    modifier: Modifier = Modifier,
    produceList: List<Produce> = emptyList(),
    onItemClick: () -> Unit = {}
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (produceList.isEmpty()){
            item {
                Text(text = "No produce found", color = PrimaryTextColor, textAlign = TextAlign.Center)
            }
        }

        produceList.forEach { produce ->
            if (produce.ownerId == GlobalVariables.ownerId){
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
                        produceDate = produce.produceBestBeforeDate,
                        onItemClick = {
                            GlobalVariables.produceIdToView = produce.produceId
                            onItemClick()
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else{
                item {
                    Text(text = "You haven't posted anything yet", color = PrimaryTextColor, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview
@Composable
private fun MyProduceScreenMinorPreview() {
    MyProduceScreenMinor()
}