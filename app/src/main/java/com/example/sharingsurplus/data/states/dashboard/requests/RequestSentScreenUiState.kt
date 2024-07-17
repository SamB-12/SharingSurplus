package com.example.sharingsurplus.data.states.dashboard.requests

import com.example.sharingsurplus.data.model.Request
import com.example.sharingsurplus.data.repository.AuthResult

data class RequestSentScreenUiState(
    val requestList: List<Request> = emptyList(),
    val selectedRequest: Request? = null,
    val isDeleteDialogVisible: Boolean = false,
    val isEditDialogVisible: Boolean = false,
    val deleteStatus:AuthResult<Unit>? = null,
    val editStatus:AuthResult<Unit>? = null
)
