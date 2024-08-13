package com.example.sharingsurplus.data.states.dashboard.profile

import android.net.Uri
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.states.status.ProduceType

data class EditAndDeleteProduceUiState(
    val produceName: String = "",
    val produceDescription: String = "",
    val produceType: ProduceType?= ProduceType.None, // or can i have an enum?
    val produceQuantity: Int = 0,
    val produceUnit: String = "",
    val produceLocation: String = "",//for now
    val produceLatitude: Double = 0.0,
    val produceLongitude: Double = 0.0,
    val produceLocationName: String = "",
    //add permission booleans
    val producePickupInstructions: String = "",
    val produceBestBeforeDate: String = "",
    val isDatePickerDialogVisible: Boolean = false,
    val isLocationDialogVisible: Boolean = false,
    val isAddImageDialogVisible: Boolean = false,
    val produceImageUrl: String = "",
    val produceImageUri: Uri? = Uri.EMPTY,
    val tempImageUri: Uri? = Uri.EMPTY,
    val isEditConfirmDialogVisible: Boolean = false,
    val editResult: Result<Unit>?= null,
    val isDeleteConfirmDialogVisible: Boolean = false,
    val deleteResult: Result<Unit>?= null,
    val isLocationEnabledDialogVisible: Boolean = false,

    val producerName:String = "",
)
