package com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private suspend fun fetchAddress(location: Location, geocoder: Geocoder): String{
        val addressesList: MutableList<Address>? = geocoder.getFromLocation(location.latitude,location.longitude,1)//have to use the deprecated method cause the new one only works for api 33
        return if (!addressesList.isNullOrEmpty()){
            addressesList[0].getAddressLine(0)
        } else {
            "Unknown location"
        }
    }
}