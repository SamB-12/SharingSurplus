package com.example.sharingsurplus.data.states.dashboard.requests

data class RequestDisplayState(
    val produceId: String = "",
    val requestId: String = "",
    val ownerId: String = "",
    val produceName: String = "",
    val requesterId: String = "",
    val requesterName: String = "",
    val requestedQuantity: String = "",
    val requestedDate: String = "",
    val requestedTime: String = "",
    val requestedRequirement: String = "",
    val imageUrl: String = "",
)
