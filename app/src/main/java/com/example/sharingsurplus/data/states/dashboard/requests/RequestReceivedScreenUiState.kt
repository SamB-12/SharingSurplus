package com.example.sharingsurplus.data.states.dashboard.requests

import com.example.sharingsurplus.data.model.Request

data class RequestReceivedScreenUiState(
    val requestList: List<Request> = emptyList(),
    //val displayList: MutableList<RequestDisplayState> = mutableListOf(),
    val selectedRequest: Request = Request(),
    val isAcceptDialogVisible: Boolean = false,
    val isRejectDialogVisible: Boolean = false
)
