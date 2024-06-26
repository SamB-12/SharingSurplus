package com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.profile.EditProfileScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val authRepository: AuthRepository
):ViewModel() {

    private val _editProfileUiState = MutableStateFlow<EditProfileScreenUiState>(EditProfileScreenUiState())
    val editProfileUiState = _editProfileUiState.asStateFlow()

    val editedProfile = MutableStateFlow(User())

    init {
        viewModelScope.launch {
            _editProfileUiState.value = _editProfileUiState.value.copy(isLoading = true)
            inflateFields()
            _editProfileUiState.value = _editProfileUiState.value.copy(isLoading = false)
        }
    }

    suspend fun inflateFields(){
        val result = firestoreRepository.getUser(authRepository.currentUser!!.uid)
        if (result is AuthResult.Success){
            editedProfile.value = result.data
            _editProfileUiState.value = _editProfileUiState.value.copy(
                name = editedProfile.value.name,
                email = editedProfile.value.email,
                phone = editedProfile.value.phone?:"",
                address = editedProfile.value.address?:"",
            )
        }
    }

    fun updateProfile(){
        viewModelScope.launch {
            _editProfileUiState.value = _editProfileUiState.value.copy(isLoading = true)
            val result = firestoreRepository.updateUser(editedProfile.value.uid, editedProfile.value)
            _editProfileUiState.value = _editProfileUiState.value.copy(isSuccess = result)
            _editProfileUiState.value = _editProfileUiState.value.copy(isLoading = false)
        }
    }

    fun onNameChanged(name:String){
        editedProfile.value = editedProfile.value.copy(name = name)
        _editProfileUiState.value = _editProfileUiState.value.copy(name = name)
    }

    fun onEmailChanged(email:String){
        editedProfile.value = editedProfile.value.copy(email = email)
        _editProfileUiState.value = _editProfileUiState.value.copy(email = email)
    }

    fun onPhoneChanged(phone:String){
        editedProfile.value = editedProfile.value.copy(phone = phone)
        _editProfileUiState.value = _editProfileUiState.value.copy(phone = phone)
    }

    fun onAddressChanged(address:String){
        editedProfile.value = editedProfile.value.copy(address = address)
        _editProfileUiState.value = _editProfileUiState.value.copy(address = address)
    }

    fun pressBack(){
        _editProfileUiState.value = _editProfileUiState.value.copy(isBackPressed = true)
    }

    fun cancelBack(){
        _editProfileUiState.value = _editProfileUiState.value.copy(isBackPressed = false)
    }

    fun showDialog(){
        _editProfileUiState.value = _editProfileUiState.value.copy(isAlertDialogVisible = true)
    }

    fun hideDialog(){
        _editProfileUiState.value = _editProfileUiState.value.copy(isAlertDialogVisible = false)
    }

}

