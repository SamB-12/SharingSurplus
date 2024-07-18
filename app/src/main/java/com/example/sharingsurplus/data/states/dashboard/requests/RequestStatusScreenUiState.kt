package com.example.sharingsurplus.data.states.dashboard.requests

import com.example.sharingsurplus.data.model.Request

data class RequestStatusScreenUiState(
    val requestList: List<Request> = emptyList(),
)
