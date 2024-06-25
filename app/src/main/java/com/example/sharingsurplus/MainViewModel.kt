package com.example.sharingsurplus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.states.MainActivityState
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) :ViewModel() {

    private val _mainState = MutableStateFlow(MainActivityState())
    val mainState = _mainState.asStateFlow()

    init {
        _mainState.value = _mainState.value.copy(isLoading = true)
        viewModelScope.launch {
            delay(2000)
            if (authRepository.currentUser!=null){
                _mainState.value = _mainState.value.copy(route = Routes.MainMenu.route)
            }else{
                _mainState.value = _mainState.value.copy(route = Graphs.AuthenticationGraph.graph)
            }
            _mainState.value = _mainState.value.copy(isLoading = false)
        }
    }

}