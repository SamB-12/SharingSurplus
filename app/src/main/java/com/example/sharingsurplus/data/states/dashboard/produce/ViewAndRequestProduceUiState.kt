package com.example.sharingsurplus.data.states.dashboard.produce

import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.states.status.ProduceType

data class ViewAndRequestProduceUiState(
    val produceId: String = "",
    val imageUrl: String = "",
    val ownerId: String = "",
    val produceName: String = "",
    val produceDescription: String = "",
    val produceType: ProduceType = ProduceType.None,
    val produceQuantity: Int = 0,
    val produceUnit: String = "",
    val producePickupInstructions: String = "",
    val produceBestBeforeDate: String = "",
    val producerName: String = "",
    val produceLocation: String = "",
    val produceLatitude: Double = 0.0,
    val produceLongitude: Double = 0.0,
    val producePickUpDate: String = "",
    val producePickupTime: String = "",
    val requestedQuantity: Int = 0,
    val producePickUpRequirements: String = "",
    val isDatePickerVisible: Boolean = false,
    val isTimePickerVisible: Boolean = false,
    val isConfirmDialogVisible: Boolean = false,
    val requestResult: AuthResult<Unit>? = null
)
