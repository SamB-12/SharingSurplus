package com.example.sharingsurplus.data.model

import com.example.sharingsurplus.data.states.status.RequestStatus

data class Request(
    val requestId: String = "",
    val produceId: String = "",
    val requesterId: String = "",
    val ownerId: String = "",
    val requestedDate: String = "",
    val requestedTime: String = "",
    val requestedQuantity: Int = 0,
    val requestedUnit: String = "",
    val requestInstructions: String = "",
    val requestedImageUrl: String = "",
    val status: RequestStatus = RequestStatus.Pending,
    val reason: String = "",

    val requesterName: String = "",
    val ownerName: String = "",
    val produceName: String = "",
)
