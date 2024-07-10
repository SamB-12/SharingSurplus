package com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.model.Produce
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.repository.storage.FirebaseStorageRepository
import com.example.sharingsurplus.data.states.dashboard.produce.AddProduceUiState
import com.example.sharingsurplus.data.states.dashboard.produce.ProduceType
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProduceViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository,
    private val authRepository: AuthRepository
) : ViewModel(){//the most important view model!

    private val _addProduceUiState = MutableStateFlow(AddProduceUiState())
    val addProduceUiState = _addProduceUiState.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog(){
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ){
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)){
            visiblePermissionDialogQueue.add(permission)
        }
    }

    fun uploadProduce(){
        if (validateInput()){//since we are validating the inputs, we can be certain that the inputs are valid
            viewModelScope.launch {
                _addProduceUiState.value = _addProduceUiState.value.copy(uploadResult = AuthResult.Loading)
                val imageResult = firebaseStorageRepository.uploadImageToStorage(authRepository.currentUser!!.uid,addProduceUiState.value.produceImageUri!!)
                _addProduceUiState.value = _addProduceUiState.value.copy(producerName = getProducerName())
                when(imageResult){
                    is AuthResult.Success -> {
                        val produce = Produce(
                            produceName = addProduceUiState.value.produceName,
                            producerName = addProduceUiState.value.producerName,
                            produceDescription = addProduceUiState.value.produceDescription,
                            produceType = addProduceUiState.value.produceType,
                            produceQuantity = addProduceUiState.value.produceQuantity,
                            produceUnit = addProduceUiState.value.produceUnit,
                            produceBestBeforeDate = addProduceUiState.value.produceBestBeforeDate,
                            producePickupInstructions = addProduceUiState.value.producePickupInstructions,
                            produceLocation = addProduceUiState.value.produceLocation,
                            produceLatitude = addProduceUiState.value.produceLatitude,
                            produceLongitude = addProduceUiState.value.produceLongitude,
                            produceImageUrl = imageResult.data
                        )
                        firestoreRepository.addProduce(produce,authRepository.currentUser!!.uid)
                        _addProduceUiState.value = _addProduceUiState.value.copy(uploadResult = AuthResult.Success(Unit))
                    }
                    is AuthResult.Error -> {
                        _addProduceUiState.value = _addProduceUiState.value.copy(uploadResult = AuthResult.Error(imageResult.message))
                    } else -> {
                        //Can only add if there's an image
                    }
                }
            }
        } else {
            _addProduceUiState.value = _addProduceUiState.value.copy(uploadResult = AuthResult.Error("Please fill out all fields"))
        }
    }//TODO: The produce name is getting set to the location and get current location has a problem with it

    suspend fun getProducerName():String{
        val result = firestoreRepository.getUser(authRepository.currentUser!!.uid)
        if (result is AuthResult.Success){
            return result.data.name
        } else{
            return ""
        }
    }

    fun onProduceNameChanged(produceName: String){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            produceName = produceName
        )
    }

    fun onProduceDescriptionChanged(produceDescription: String){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            produceDescription = produceDescription
        )
    }

    fun onProduceTypeChanged(produceType: ProduceType){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            produceType = produceType
        )
    }

    fun onProduceQuantityChanged(produceQuantity: String){
        try {
            _addProduceUiState.value = _addProduceUiState.value.copy(
                produceQuantity = produceQuantity.toInt()
            )
        } catch (e: Exception){
            _addProduceUiState.value = _addProduceUiState.value.copy(
                produceQuantity = 0
            )
        }
    }

    fun onProduceUnitChanged(produceUnit: String){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            produceUnit = produceUnit
        )
    }

    fun onProducePickupInstructionChanged(pickupInstructions: String){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            producePickupInstructions = pickupInstructions
        )
    }

    fun onProduceBestBeforeDateChanged(bestBeforeDate: String){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            produceBestBeforeDate = bestBeforeDate
        )
    }

    fun isDatePickerDialogVisible(visible: Boolean){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            isDatePickerDialogVisible = visible
        )
        Log.i("this was pressed", "isDatePickerDialogVisible: $visible")
    }

    fun isLocationPickerDialogVisible(visible: Boolean){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            isLocationDialogVisible = visible
        )
        Log.i("this was pressed", "isLocationPickerDialogVisible: $visible")
    }

    fun isImagePickerDialogVisible(visible: Boolean){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            isAddImageDialogVisible = visible
        )
        Log.i("this was pressed", "isImagePickerDialogVisible: $visible")
    }

    fun isUploadConfirmDialogVisible(visible: Boolean){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            isUploadConfirmDialogVisible = visible
        )
    }

    fun onLocationChanged(location: String){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            produceLocation = location
        )
    }

    fun onLocationNameChanged(locationName: String){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            produceLocationName = locationName
        )
    }

    fun onLatLongChanged(latLng: LatLng){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            produceLatitude = latLng.latitude,
            produceLongitude = latLng.longitude
        )
    }

    fun onImageSelected(image: String){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            produceImageUrl = image
        )
    }

    fun onImageSelectedUri(imageUri: Uri){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            produceImageUri = imageUri
        )
    }

    fun onTempImageUriChanged(tempImageUri: Uri){
        _addProduceUiState.value = _addProduceUiState.value.copy(
            tempImageUri = tempImageUri
        )
    }


     fun getAddressFromLocation(location: Location,geocoder: Geocoder){
        viewModelScope.launch {
            val address = fetchAddress(location,geocoder)
            _addProduceUiState.value = _addProduceUiState.value.copy(
                produceLocation = address
            )
        }
    }

    fun validateInput(): Boolean{
        if (_addProduceUiState.value.produceName.isEmpty()
            || _addProduceUiState.value.produceDescription.isEmpty()
            || _addProduceUiState.value.produceType == ProduceType.None
            || _addProduceUiState.value.produceQuantity == 0
            || _addProduceUiState.value.produceUnit.isEmpty()
            || _addProduceUiState.value.produceLocation.isEmpty()
            ||_addProduceUiState.value.produceLatitude == 0.0
            || _addProduceUiState.value.produceLongitude == 0.0
            || _addProduceUiState.value.produceBestBeforeDate.isEmpty()
            || _addProduceUiState.value.producePickupInstructions.isEmpty()
            || _addProduceUiState.value.produceImageUri == Uri.EMPTY){
            return false
        } else {
            return true
        }
    }

    private suspend fun fetchAddress(location: Location, geocoder: Geocoder): String{
        val addressesList: MutableList<Address>? = geocoder.getFromLocation(location.latitude,location.longitude,1)//have to use the deprecated method cause the new one only works for api 33
        return if (!addressesList.isNullOrEmpty()){
            addressesList[0].getAddressLine(0)
        } else {
            "Unknown location"
        }
    }
}