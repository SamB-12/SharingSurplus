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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.states.status.RequestStatus
import com.example.sharingsurplus.presentation.ui.components.RequestReceivedItemCardComponent
import com.example.sharingsurplus.presentation.ui.components.RequestSentItemCardComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@Composable
fun RequestSentScreenMinor(
    modifier: Modifier = Modifier,
    requestList: List<Request> = emptyList(),
    onDeleteClick: (Request) -> Unit = {},
    onEditClick: (Request) -> Unit = {},
) {

    val filteredList = requestList.filter { request ->
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
                RequestSentItemCardComponent(
                    produceName = request.produceName,
                    painter = rememberAsyncImagePainter(model = request.requestedImageUrl),
                    requestedQuantity = request.requestedQuantity.toString() + request.requestedUnit,
                    requestedDate = request.requestedDate,
                    requestedTime = request.requestedTime,
                    onEdit = { onEditClick(request) },
                    onDelete = { onDeleteClick(request) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
private fun RequestSentScreenMinorPreview() {
    RequestSentScreenMinor()
}