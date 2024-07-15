package com.example.sharingsurplus.data.states.dashboard.produce

data class ViewAndRequestProduceUiState(
    val produceId: String = "",
    val imageUrl: String = "",
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
    val producePickUpRequirements: String = "",
    val isDatePickerVisible: Boolean = false,
    val isTimePickerVisible: Boolean = false,
)
