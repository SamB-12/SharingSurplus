package com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.profile.ProfileInfoScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * This view model is responsible to view the profile screen.
 */
@HiltViewModel
class ProfileInfoScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
):ViewModel() {

    private val _profileInfoUiState = MutableStateFlow(ProfileInfoScreenUiState())
    val profileInfoUiState = _profileInfoUiState.asStateFlow()

    val loggedInUser = MutableStateFlow(User())

    init {//change it to info from the firestore!
        //_profileInfoUiState.value = _profileInfoUiState.value.copy(name = authRepository.currentUser?.displayName.toString(), email = authRepository.currentUser?.email.toString())
        viewModelScope.launch {
            getLoggedInUser() // it is better to update the ui in this coroutine than do it here cause it causes sync issues
        }
    }

    private suspend fun getLoggedInUser() {
        val result = firestoreRepository.getUser(authRepository.currentUser!!.uid.toString())
        if (result is Result.Success) {
            loggedInUser.value = result.data

            val date = Date(loggedInUser.value.createdAt)
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateString = sdf.format(date).toString()

            _profileInfoUiState.value = _profileInfoUiState.value.copy(
                name = loggedInUser.value.name,
                email = loggedInUser.value.email,
                phone = loggedInUser.value.phone?:"",
                address = loggedInUser.value.address?:"",
                dateJoined = dateString
            )
        } else {
            //Nothing
        }
    }
}