package com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sharingsurplus.data.states.dashboard.produce.MapScreenUiState
import com.example.sharingsurplus.presentation.utils.GlobalVariables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * This is the view model for the Map screen.
 */
@HiltViewModel
class MapViewModel @Inject constructor(

) : ViewModel() {

    private val _mapUiState = MutableStateFlow(MapScreenUiState())
    val mapUiState = _mapUiState.asStateFlow()

    init {
        setUpMap()
    }

    fun setUpMap(){
        _mapUiState.value = _mapUiState.value.copy(produceLatitude = GlobalVariables.produceLatitude)
        _mapUiState.value = _mapUiState.value.copy(produceLongitude = GlobalVariables.produceLongitude)
        _mapUiState.value = _mapUiState.value.copy(produceLocation = GlobalVariables.produceLocation)
    }
}