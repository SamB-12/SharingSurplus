package com.example.sharingsurplus.data.states.dashboard.requests

import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.repository.AuthResult

data class RequestReceivedScreenUiState(
    val requestList: List<Request> = emptyList(),
    val selectedRequest: Request = Request(),
    val isAcceptDialogVisible: Boolean = false,
    val isRejectDialogVisible: Boolean = false,
    val reason: String = "",
    val rejectResult: AuthResult<Unit>? = null,
    val acceptResult: AuthResult<Unit>? = null
)
