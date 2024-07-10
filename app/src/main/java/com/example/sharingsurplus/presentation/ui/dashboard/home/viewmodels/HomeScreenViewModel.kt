package com.example.sharingsurplus.presentation.ui.dashboard.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.home.HomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
):ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenUiState())
    val homeScreenUiState = _homeScreenState.asStateFlow()

    init {
        fetchProduceList()
    }

    fun fetchProduceList(){
        viewModelScope.launch {
            firestoreRepository.getProduceList().collect{produceList -> //the method in repo emits flow, so we collect it here
                _homeScreenState.value = _homeScreenState.value.copy(produceList = produceList)
            }
        }
    }

}