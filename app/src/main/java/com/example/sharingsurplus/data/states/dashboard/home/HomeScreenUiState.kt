package com.example.sharingsurplus.data.states.dashboard.home

import com.example.sharingsurplus.data.model.Produce

data class HomeScreenUiState(
    val produceList: List<Produce> = emptyList(),
    val isLoading: Boolean = false
)
