package com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.model.Produce
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.repository.storage.FirebaseStorageRepository
import com.example.sharingsurplus.data.states.dashboard.produce.ProduceType
import com.example.sharingsurplus.data.states.dashboard.profile.EditAndDeleteProduceUiState
import com.example.sharingsurplus.presentation.utils.GlobalVariables
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAndDeleteProduceViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository,
    private val authRepository: AuthRepository
) : ViewModel(){

    private val _editAndDeleteProduceUiState = MutableStateFlow(EditAndDeleteProduceUiState())
    val editAndDeleteProduceUiState = _editAndDeleteProduceUiState.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    val existingImageUrl = mutableStateOf("")

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

    init {
        getProduceInfo()
    }

    private fun getProduceInfo(){
        viewModelScope.launch {
            val result = firestoreRepository.getProduce(GlobalVariables.produceIdToView)

            if (result is AuthResult.Success){
                val produce = result.data
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(produceImageUrl = produce.produceImageUrl)
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(produceName = produce.produceName)
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(produceDescription = produce.produceDescription)
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(produceType = produce.produceType!!)
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(produceQuantity = produce.produceQuantity)
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(produceUnit = produce.produceUnit)
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(producePickupInstructions = produce.producePickupInstructions)
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(produceBestBeforeDate = produce.produceBestBeforeDate)
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(producerName = produce.producerName)
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(produceLocation = produce.produceLocation)
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(produceLatitude = produce.produceLatitude)
                _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(produceLongitude = produce.produceLongitude)
                existingImageUrl.value = produce.produceImageUrl
            }

        }
    }


    fun updateProduce() {
        if (validateInput()) {//since we are validating the inputs, we can be certain that the inputs are valid
            viewModelScope.launch {
                if (editAndDeleteProduceUiState.value.produceImageUrl != existingImageUrl.value){
                    val imageResult = firebaseStorageRepository.uploadImageToStorage(
                        authRepository.currentUser!!.uid,
                        editAndDeleteProduceUiState.value.produceImageUri!!
                    )
                    when (imageResult) {
                        is AuthResult.Success -> {
                            val produce = Produce(
                                produceId = GlobalVariables.produceIdToView,
                                ownerId = authRepository.currentUser!!.uid,
                                produceName = editAndDeleteProduceUiState.value.produceName,
                                producerName = editAndDeleteProduceUiState.value.producerName,
                                produceDescription = editAndDeleteProduceUiState.value.produceDescription,
                                produceType = editAndDeleteProduceUiState.value.produceType,
                                produceQuantity = editAndDeleteProduceUiState.value.produceQuantity,
                                produceUnit = editAndDeleteProduceUiState.value.produceUnit,
                                produceBestBeforeDate = editAndDeleteProduceUiState.value.produceBestBeforeDate,
                                producePickupInstructions = editAndDeleteProduceUiState.value.producePickupInstructions,
                                produceLocation = editAndDeleteProduceUiState.value.produceLocation,
                                produceLatitude = editAndDeleteProduceUiState.value.produceLatitude,
                                produceLongitude = editAndDeleteProduceUiState.value.produceLongitude,
                                produceImageUrl = imageResult.data
                            )
                            val result = firestoreRepository.updateProduce(produceId = GlobalVariables.produceIdToView, updatedProduce = produce)
                            _editAndDeleteProduceUiState.value =
                                _editAndDeleteProduceUiState.value.copy(
                                    editResult = result
                                )
                            Log.i("SUCCESS", "It was a success")
                        }

                        is AuthResult.Error -> {
                            _editAndDeleteProduceUiState.value =
                                _editAndDeleteProduceUiState.value.copy(
                                    editResult = AuthResult.Error(imageResult.message)
                                )
                            Log.i("SUCCESS", "It was a Failure")
                        }

                        else -> {
                            //Nothing
                        }
                    }

                } else{
                    val produce = Produce(
                        produceId = GlobalVariables.produceIdToView,
                        ownerId = authRepository.currentUser!!.uid,
                        produceName = editAndDeleteProduceUiState.value.produceName,
                        producerName = editAndDeleteProduceUiState.value.producerName,
                        produceDescription = editAndDeleteProduceUiState.value.produceDescription,
                        produceType = editAndDeleteProduceUiState.value.produceType,
                        produceQuantity = editAndDeleteProduceUiState.value.produceQuantity,
                        produceUnit = editAndDeleteProduceUiState.value.produceUnit,
                        produceBestBeforeDate = editAndDeleteProduceUiState.value.produceBestBeforeDate,
                        producePickupInstructions = editAndDeleteProduceUiState.value.producePickupInstructions,
                        produceLocation = editAndDeleteProduceUiState.value.produceLocation,
                        produceLatitude = editAndDeleteProduceUiState.value.produceLatitude,
                        produceLongitude = editAndDeleteProduceUiState.value.produceLongitude,
                        produceImageUrl = editAndDeleteProduceUiState.value.produceImageUrl
                    )
                    val result = firestoreRepository.updateProduce(produceId = GlobalVariables.produceIdToView, updatedProduce = produce)
                    _editAndDeleteProduceUiState.value =
                        _editAndDeleteProduceUiState.value.copy(
                            editResult = result
                        )
                    Log.i("SUCCESS", "It was a success")
                }
            }
        } else{
            _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
                editResult = AuthResult.Error("Please fill in all fields")
            )
        }
    }

    fun deleteProduce(){
        viewModelScope.launch {
            val result = firestoreRepository.deleteProduce(GlobalVariables.produceIdToView)

            when(result){
                is AuthResult.Success -> {
                    _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(deleteResult = AuthResult.Success(Unit))
                }
                is AuthResult.Error -> {
                    _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(deleteResult = AuthResult.Error(result.message))
                }
                else -> {
                    //Nothing
                }
            }
        }
    }

    suspend fun getProducerName():String{
        val result = firestoreRepository.getUser(authRepository.currentUser!!.uid)
        if (result is AuthResult.Success){
            return result.data.name
        } else{
            return ""
        }
    }

    fun onProduceNameChanged(produceName: String){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            produceName = produceName
        )
    }

    fun onProduceDescriptionChanged(produceDescription: String){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            produceDescription = produceDescription
        )
    }

    fun onProduceTypeChanged(produceType: ProduceType){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            produceType = produceType
        )
    }

    fun onProduceQuantityChanged(produceQuantity: String){
        try {
            _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
                produceQuantity = produceQuantity.toInt()
            )
        } catch (e: Exception){
            _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
                produceQuantity = 0
            )
        }
    }

    fun onProduceUnitChanged(produceUnit: String){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            produceUnit = produceUnit
        )
    }

    fun onProducePickupInstructionChanged(pickupInstructions: String){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            producePickupInstructions = pickupInstructions
        )
    }

    fun onProduceBestBeforeDateChanged(bestBeforeDate: String){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            produceBestBeforeDate = bestBeforeDate
        )
    }

    fun isDatePickerDialogVisible(visible: Boolean){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            isDatePickerDialogVisible = visible
        )
        Log.i("this was pressed", "isDatePickerDialogVisible: $visible")
    }

    fun isLocationPickerDialogVisible(visible: Boolean){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            isLocationDialogVisible = visible
        )
        Log.i("this was pressed", "isLocationPickerDialogVisible: $visible")
    }

    fun isImagePickerDialogVisible(visible: Boolean){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            isAddImageDialogVisible = visible
        )
        Log.i("this was pressed", "isImagePickerDialogVisible: $visible")
    }

    fun isEditConfirmDialogVisible(visible: Boolean){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            isEditConfirmDialogVisible = visible
        )
    }
    fun isDeleteConfirmDialogVisible(visible: Boolean){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            isDeleteConfirmDialogVisible = visible
        )
    }

    fun onLocationChanged(location: String){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            produceLocation = location
        )
    }

    fun onLocationNameChanged(locationName: String){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            produceLocationName = locationName
        )
    }

    fun onLatLongChanged(latLng: LatLng){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            produceLatitude = latLng.latitude,
            produceLongitude = latLng.longitude
        )
    }

    fun onImageSelected(image: String){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            produceImageUrl = image
        )
    }

    fun onImageSelectedUri(imageUri: Uri){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            produceImageUri = imageUri
        )
    }

    fun onTempImageUriChanged(tempImageUri: Uri){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            tempImageUri = tempImageUri
        )
    }


    fun getAddressFromLocation(location: Location, geocoder: Geocoder){
        viewModelScope.launch {
            val address = fetchAddress(location,geocoder)
            _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
                produceLocation = address
            )
        }
    }

    fun validateInput(): Boolean{
        if (_editAndDeleteProduceUiState.value.produceName.isEmpty()
            || _editAndDeleteProduceUiState.value.produceDescription.isEmpty()
            || _editAndDeleteProduceUiState.value.produceType == ProduceType.None
            || _editAndDeleteProduceUiState.value.produceQuantity == 0
            || _editAndDeleteProduceUiState.value.produceUnit.isEmpty()
            || _editAndDeleteProduceUiState.value.produceLocation.isEmpty()
            ||_editAndDeleteProduceUiState.value.produceLatitude == 0.0
            || _editAndDeleteProduceUiState.value.produceLongitude == 0.0
            || _editAndDeleteProduceUiState.value.produceBestBeforeDate.isEmpty()
            || _editAndDeleteProduceUiState.value.producePickupInstructions.isEmpty()
            || _editAndDeleteProduceUiState.value.produceImageUrl.isEmpty()){
            return false
        } else {
            return true
        }
    }

    fun onLocationVisibleDialogChanged(visible: Boolean){
        _editAndDeleteProduceUiState.value = _editAndDeleteProduceUiState.value.copy(
            isLocationDialogVisible = visible
        )
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