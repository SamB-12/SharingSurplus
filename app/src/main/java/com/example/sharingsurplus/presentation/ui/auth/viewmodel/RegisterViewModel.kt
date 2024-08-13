package com.example.sharingsurplus.presentation.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.states.auth.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This ViewModel is used to register a new user.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {

    private val _registerUiState = MutableStateFlow(RegisterUiState())
    val registerUiState = _registerUiState.asStateFlow()

    fun register() {
        val currentState = registerUiState.value

        if (validateForm(currentState.name, currentState.email, currentState.password, currentState.confirmPassword, currentState.isChecked) && matchPasswords(currentState.password, currentState.confirmPassword)){
            viewModelScope.launch {
                _registerUiState.value = _registerUiState.value.copy(result = Result.Loading, isLoading = true)
                val result = authRepository.register(email = currentState.email, name = currentState.name, password = currentState.password)
                _registerUiState.value = _registerUiState.value.copy(result = result, isLoading = false)
            }
        } else{
            if (!validateForm(currentState.name, currentState.email, currentState.password, currentState.confirmPassword, currentState.isChecked)){
                _registerUiState.value = _registerUiState.value.copy(result = Result.Error("Please fill all fields"))
            } else if (!matchPasswords(currentState.password,currentState.confirmPassword)){
                _registerUiState.value = _registerUiState.value.copy(result = Result.Error("Passwords do not match"))
            }
        }
    }

    fun onNameChanged(name: String) {
        _registerUiState.value = _registerUiState.value.copy(name = name)
    }

    fun onEmailChanged(email: String) {
        _registerUiState.value = _registerUiState.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _registerUiState.value = _registerUiState.value.copy(password = password)
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _registerUiState.value = _registerUiState.value.copy(confirmPassword = confirmPassword)
    }

    fun onCheckChanged(isChecked: Boolean) {
        _registerUiState.value = _registerUiState.value.copy(isChecked = isChecked)
    }

    private fun validateForm(name: String, email: String, password: String, confirmPassword: String, isChecked: Boolean):Boolean{
        return name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && isChecked
    }

    private fun matchPasswords(password: String, confirmPassword: String):Boolean{
        return password == confirmPassword
    }

    fun resetAuthResult(){
        _registerUiState.value = _registerUiState.value.copy(result = null)
    }


}