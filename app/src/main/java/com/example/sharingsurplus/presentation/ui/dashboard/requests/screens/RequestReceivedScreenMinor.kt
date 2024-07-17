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
import coil.compose.rememberAsyncImagePainter
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.states.dashboard.requests.RequestDisplayState
import com.example.sharingsurplus.data.states.status.RequestStatus
import com.example.sharingsurplus.presentation.ui.components.RequestReceivedItemCardComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@Composable
fun RequestReceivedScreenMinor(
    modifier: Modifier = Modifier,
    requestList: List<Request> = emptyList(),
    onAcceptSelected: (Request) -> Unit = {},
    onDeclineSelected: (Request) -> Unit = {}
) {

    val filteredList = requestList.filter {request ->
        request.status == RequestStatus.Pending
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PrimaryColor)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        filteredList.forEach {request ->
            item {
                Spacer(modifier = Modifier.height(8.dp))
                RequestReceivedItemCardComponent(
                    produceName = request.produceName,
                    painter = rememberAsyncImagePainter(model = request.requestedImageUrl),
                    requesterName = request.requesterName,
                    requestedQuantity = request.requestedQuantity.toString() + request.requestedUnit,
                    requestedDate = request.requestedDate,
                    requestedTime = request.requestedTime,
                    onAccept = {onAcceptSelected(request)},
                    onReject = {onDeclineSelected(request)}
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