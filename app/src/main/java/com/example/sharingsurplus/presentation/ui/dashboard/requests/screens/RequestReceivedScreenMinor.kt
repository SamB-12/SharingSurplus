package com.example.sharingsurplus.presentation.ui.dashboard.requests.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.components.RequestReceivedItemCardComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@Composable
fun RequestReceivedScreenMinor(
    modifier: Modifier = Modifier
) {

    val randomList = List(10){

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PrimaryColor)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        randomList.forEach {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                RequestReceivedItemCardComponent(
                    produceName = "Andy",
                    painter = painterResource(id = R.drawable.ic_andy),
                    requesterName = "Andreas Weisberg",
                    requestedQuantity = "1 kg",
                    requestedDate = "13/07/2024",
                    requestedTime = "11:11"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}

@Preview
@Composable
private fun RequestReceivedScreenMinorPreview() {
    RequestReceivedScreenMinor()
}