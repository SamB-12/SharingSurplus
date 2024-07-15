package com.example.sharingsurplus.data.states.dashboard.produce

data class MapScreenUiState(
    val produceLocation: String = "",
    val produceLatitude: Double = 0.0,
    val produceLongitude: Double = 0.0,
    val produceZoomState: Float = 15f,
)
