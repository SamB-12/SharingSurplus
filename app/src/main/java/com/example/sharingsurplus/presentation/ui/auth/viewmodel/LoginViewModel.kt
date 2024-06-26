package com.example.sharingsurplus.presentation.ui.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.states.auth.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    fun login() {
        val currentState = _loginUiState.value
        if (validateForm(currentState.email,currentState.password)){
            viewModelScope.launch {
                _loginUiState.value = _loginUiState.value.copy(authResult = AuthResult.Loading, isLoading = true)
                val result = authRepository.login(email = currentState.email, password = currentState.password)
                _loginUiState.value = _loginUiState.value.copy(authResult = result, isLoading = false)
            }
        } else{
            _loginUiState.value = _loginUiState.value.copy(authResult = AuthResult.Error("Invalid Credentials"))
        }
    }

    fun onEmailChanged(email: String) {
        _loginUiState.value = _loginUiState.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _loginUiState.value = _loginUiState.value.copy(password = password)
    }

    fun resetAuthResult(){
        _loginUiState.value = _loginUiState.value.copy(authResult = null)
    }

    private fun validateForm(email: String, password: String):Boolean{
        return email.isNotEmpty() && password.isNotEmpty()
    }
}