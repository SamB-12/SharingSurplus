package com.example.sharingsurplus.data.states.dashboard.produce

data class AddProduceUiState(
    val produceName: String = "",
    val produceDescription: String = "",
    val produceType: ProduceType ?= ProduceType.None, // or can i have an enum?
    val produceQuantity: Int = 0,
    val produceUnit: String = "",
    val produceLocation: String = "",//for now
    //add permission booleans
    val producePickupInstructions: String = "",
    val produceBestBeforeDate: String = "",
    val isDatePickerDialogVisible: Boolean = false,
    val isLocationDialogVisible: Boolean = false,
    val isAddImageDialogVisible: Boolean = false,
    val produceImageUrl: String = "",
)
