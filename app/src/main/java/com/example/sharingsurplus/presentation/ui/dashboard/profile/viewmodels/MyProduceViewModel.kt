package com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.profile.MyProduceScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProduceViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ViewModel(){

    private val _myProduceUiState = MutableStateFlow(MyProduceScreenUiState())
    val myProduceUiState = _myProduceUiState.asStateFlow()

    init {
        getProduceList()
    }

    private fun getProduceList(){
        viewModelScope.launch {
            firestoreRepository.getProduceList().collectLatest{produceList ->
                _myProduceUiState.value = _myProduceUiState.value.copy(produceList = produceList)
            }
        }
    }

}