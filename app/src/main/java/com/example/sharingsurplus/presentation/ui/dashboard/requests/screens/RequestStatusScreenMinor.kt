package com.example.sharingsurplus.presentation.ui.dashboard.requests.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.states.status.RequestStatus
import com.example.sharingsurplus.presentation.ui.components.RequestStatusCardComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@Composable
fun RequestStatusScreenMinor(
    modifier: Modifier = Modifier,
    requestList: List<Request> = emptyList()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = PrimaryColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        requestList.forEach {request ->
            item {
                Spacer(modifier = Modifier.height(8.dp))
                RequestStatusCardComponent(
                    produceName = request.produceName,
                    painter = rememberAsyncImagePainter(model = request.requestedImageUrl),
                    requestedQuantity = request.requestedQuantity.toString() + request.requestedUnit,
                    requestedDate = request.requestedDate,
                    requestedTime = request.requestedTime,
                    requestStatus = request.status,
                    reason = request.reason
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
private fun RequestStatusScreenMinorPreview() {
    RequestStatusScreenMinor()
}