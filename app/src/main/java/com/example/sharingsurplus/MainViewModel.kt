package com.example.sharingsurplus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

/**
 * This is the view model for the main activity.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) :ViewModel() {

    private val _mainState = MutableStateFlow(MainActivityState())
    val mainState = _mainState.asStateFlow()

    private val _isMainMenu = MutableLiveData(false)
    val isMainMenu: LiveData<Boolean> get() = _isMainMenu

    init {
        _mainState.value = _mainState.value.copy(isLoading = true)
        viewModelScope.launch {
            delay(2000)
            if (authRepository.currentUser!=null){
                _mainState.value = _mainState.value.copy(route = Routes.MainMenu.route, isNavHostMain = true)
                _isMainMenu.value = true
            }else{
                _mainState.value = _mainState.value.copy(route = Graphs.AuthenticationGraph.graph, isNavHostMain = false)
                _isMainMenu.value = false
            }
            _mainState.value = _mainState.value.copy(isLoading = false)
        }
    }

    fun navigateToAuth(){
        Log.i("Trigger","It did get triggered")
        _mainState.value = _mainState.value.copy(route = Graphs.AuthenticationGraph.graph, isNavHostMain = false)
        _isMainMenu.value = false
        Log.d("Val","It did get ${_mainState.value.isNavHostMain}")
    }

    fun navigateToMainMenu(){
        _mainState.value = _mainState.value.copy(route = Routes.MainMenu.route, isNavHostMain = true)
        _isMainMenu.value = true
        Log.d("Val","It did Main ${_mainState.value.isNavHostMain}")
    }

}