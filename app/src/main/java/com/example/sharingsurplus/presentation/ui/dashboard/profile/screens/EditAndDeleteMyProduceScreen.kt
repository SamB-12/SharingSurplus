package com.example.sharingsurplus.presentation.ui.dashboard.profile.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.presentation.ui.components.ConfirmationDialogComponent
import com.example.sharingsurplus.presentation.ui.components.LocationSettingsDialogComponent
import com.example.sharingsurplus.presentation.ui.components.PermissionDialogComponent
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.produce.screens.hasPermissions
import com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels.EditAndDeleteProduceViewModel
import com.example.sharingsurplus.presentation.utils.CameraPermissionTextProvider
import com.example.sharingsurplus.presentation.utils.LocationPermissionTextProvider
import com.example.sharingsurplus.presentation.utils.createImageFile
import com.example.sharingsurplus.presentation.utils.getUriForFile
import com.example.sharingsurplus.presentation.utils.handleCameraClick
import com.example.sharingsurplus.presentation.utils.handleLocationClick
import com.example.sharingsurplus.presentation.utils.isLocationEnabled
import com.example.sharingsurplus.presentation.utils.openAppSettings
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.Locale

@SuppressLint("MissingPermission")
@Composable
fun EditAndDeleteMyProduceScreen(
    modifier: Modifier = Modifier,
    editAndDeleteProduceViewModel: EditAndDeleteProduceViewModel = hiltViewModel(),
    navController: NavHostController?
) {

    val permissionsToRequest = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    val uiState by editAndDeleteProduceViewModel.editAndDeleteProduceUiState.collectAsState()

    val localContext = LocalContext.current
    val activity = (localContext as? Activity)

    val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)//This is what we get from places api

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(localContext)
    }

    val geoCoder = remember {
        Geocoder(localContext, Locale.getDefault())
    }


    LaunchedEffect(uiState.editResult) {
        when (uiState.editResult) {
            is AuthResult.Success -> {
                Toast.makeText(localContext, "Upload successful", Toast.LENGTH_SHORT).show()
                navController?.navigateUp()
            }
            is AuthResult.Error -> {
                Toast.makeText(localContext, (uiState.editResult as AuthResult.Error).message?:"Unknown Error", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Do nothing
            }
        }
    }

    LaunchedEffect(uiState.deleteResult) {
        when (uiState.deleteResult) {
            is AuthResult.Success -> {
                Toast.makeText(localContext, "Upload successful", Toast.LENGTH_SHORT).show()
                navController?.navigateUp()
            }
            is AuthResult.Error -> {
                Toast.makeText(localContext, (uiState.deleteResult as AuthResult.Error).message?:"Unknown Error", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Do nothing
            }
        }
    }




    val multiplePermissionsResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {permissions ->
        permissionsToRequest.forEach { permission ->
            editAndDeleteProduceViewModel.onPermissionResult(
                permission = permission,
                isGranted = permissions[permission] == true
            )
        }
    }

    val cameraPermissionsResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted ->
        editAndDeleteProduceViewModel.onPermissionResult(permission = Manifest.permission.CAMERA, isGranted = isGranted)
    }

    val locationPermissionsResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted ->
        editAndDeleteProduceViewModel.onPermissionResult(permission = Manifest.permission.ACCESS_FINE_LOCATION, isGranted = isGranted)
        editAndDeleteProduceViewModel.onPermissionResult(permission = Manifest.permission.ACCESS_COARSE_LOCATION, isGranted = isGranted)
    }

    val placesLocationResultLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                val place = Autocomplete.getPlaceFromIntent(intent)
                Log.d("PlaceRetrived", "Place: ${place.name}, ${place.latLng}")
                place.address?.let { editAndDeleteProduceViewModel.onLocationChanged(it) }
                place.latLng?.let { editAndDeleteProduceViewModel.onLatLongChanged(it) }
                place.name?.let { editAndDeleteProduceViewModel.onLocationNameChanged(it) }
                editAndDeleteProduceViewModel.isLocationPickerDialogVisible(false)
            }
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            Log.d("PlaceRetrived", "Place selection canceled")
        }
    }//This retrives the requested location

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            val uri = editAndDeleteProduceViewModel.editAndDeleteProduceUiState.value.tempImageUri
            uri?.let {
                Toast.makeText(localContext, "Camera capture successful", Toast.LENGTH_SHORT).show()
                editAndDeleteProduceViewModel.onImageSelected(uri.toString())
                editAndDeleteProduceViewModel.onImageSelectedUri(it)
                editAndDeleteProduceViewModel.isImagePickerDialogVisible(false)
            }
        } else {
            Toast.makeText(localContext, "Camera capture failed", Toast.LENGTH_SHORT).show()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            editAndDeleteProduceViewModel.onImageSelected(uri.toString())
            editAndDeleteProduceViewModel.onImageSelectedUri(uri)
            Toast.makeText(localContext, "Image selected", Toast.LENGTH_SHORT).show()
            editAndDeleteProduceViewModel.isImagePickerDialogVisible(false)
        } ?: run {
            Toast.makeText(localContext, "Image selection failed", Toast.LENGTH_SHORT).show()
        }
    }//this doesn't require permissions

    if (uiState.isEditConfirmDialogVisible){
        ConfirmationDialogComponent(
            title = "Edit Produce?",
            message = "Are you sure you want to Edit this produce?",
            onCancel = {editAndDeleteProduceViewModel.isEditConfirmDialogVisible(false)},
            onConfirm = {
                editAndDeleteProduceViewModel.updateProduce()
                editAndDeleteProduceViewModel.isEditConfirmDialogVisible(false)
            }
        )
    }

    if (uiState.isDeleteConfirmDialogVisible){
        ConfirmationDialogComponent(
            title = "Delete Produce?",
            message = "Are you sure you want to delete this produce?",
            onCancel = {editAndDeleteProduceViewModel.isDeleteConfirmDialogVisible(false)},
            onConfirm ={
                editAndDeleteProduceViewModel.deleteProduce()
                editAndDeleteProduceViewModel.isDeleteConfirmDialogVisible(false)
            }
        )
    }

    if (uiState.isLocationDialogVisible){
        LocationSettingsDialogComponent(
            onDismissRequest = { editAndDeleteProduceViewModel.onLocationVisibleDialogChanged(false) },
            onConfirm = {
                editAndDeleteProduceViewModel.onLocationVisibleDialogChanged(false)
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                localContext.startActivity(intent)
            },
            title = "Enable Location Services",
            message = "Location services are important to select your current location. Please enable location services to continue"
        )
    }


    val dialogQueue = editAndDeleteProduceViewModel.visiblePermissionDialogQueue

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialogComponent(
                permissionTextProvider = when (permission) {
                    Manifest.permission.CAMERA -> {
                        CameraPermissionTextProvider()
                    }
                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                        LocationPermissionTextProvider()
                    }
                    Manifest.permission.ACCESS_COARSE_LOCATION -> {
                        LocationPermissionTextProvider()
                    }
                    else -> {
                        return@forEach
                    }
                },
                isPermanentlyDeclined = activity?.let {
                    !it.shouldShowRequestPermissionRationale(permission) && !it.hasPermissions(permission)
                } ?: false,
                onDismiss = editAndDeleteProduceViewModel::dismissDialog,
                onOkClick = {
                    editAndDeleteProduceViewModel.dismissDialog()
                    multiplePermissionsResultLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick = { openAppSettings(localContext) }
            )
        }

    ScaffoldComponent(
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = uiState.produceName, onBackClick = {navController?.navigateUp()})},
        content = {
            EditAndDeleteMyProduceScreenMinor(
                produceName = uiState.produceName,
                produceDescription = uiState.produceDescription,
                produceType = uiState.produceType!!,
                quantity = uiState.produceQuantity.toString(),
                unit = uiState.produceUnit,
                location = uiState.produceLocation,
                bestBeforeDate = uiState.produceBestBeforeDate,
                pickupInstructions = uiState.producePickupInstructions,
                isDatePickerDialogVisible = uiState.isDatePickerDialogVisible,
                produceImage = if (uiState.produceImageUri?.path?.isNotEmpty() == false) {
                    rememberAsyncImagePainter(model = uiState.produceImageUrl)
                } else {
                    rememberAsyncImagePainter(model = uiState.produceImageUri)
                },
                onProduceNameChange = editAndDeleteProduceViewModel::onProduceNameChanged,
                onProduceDescriptionChange = editAndDeleteProduceViewModel::onProduceDescriptionChanged,
                onProduceTypeChange = editAndDeleteProduceViewModel::onProduceTypeChanged,
                onQuantityChange = editAndDeleteProduceViewModel::onProduceQuantityChanged,
                onUnitChange = editAndDeleteProduceViewModel::onProduceUnitChanged,
                onLocationChange = editAndDeleteProduceViewModel::onLocationChanged,
                onPickupInstructionsChange = editAndDeleteProduceViewModel::onProducePickupInstructionChanged,
                onBestBeforeDateChange = editAndDeleteProduceViewModel::onProduceBestBeforeDateChanged,
                onIsDatePickerDialogVisibleChange = editAndDeleteProduceViewModel::isDatePickerDialogVisible,
                isImagePickerDialogVisible = uiState.isAddImageDialogVisible,
                isLocationPickerDialogVisible = uiState.isLocationDialogVisible,
                onImagePickerDialogVisibleChange = editAndDeleteProduceViewModel::isImagePickerDialogVisible,
                onLocationPickerDialogVisibleChange = editAndDeleteProduceViewModel::isLocationPickerDialogVisible,
                onGalleryClick = {
                    galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },// This method doesn't require permissions
                onCameraClick = {
                    handleCameraClick(
                        localContext,
                        onPermissionRequired = {
                            cameraPermissionsResultLauncher.launch(Manifest.permission.CAMERA)
                        },
                        onPermissionsGranted = {
                            val file = createImageFile(localContext)
                            val uri = getUriForFile(localContext, file)
                            editAndDeleteProduceViewModel.onTempImageUriChanged(uri)
                            cameraLauncher.launch(uri)
                        }
                    )
                },
                onLocationPlacesClicked = {
                    handleLocationClick(
                        localContext,
                        onPermissionRequired = {
                            locationPermissionsResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            locationPermissionsResultLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                        },
                        onPermissionsGranted = {
                            //Launch Places API
                            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                                .build(localContext)//This launches the places api intent; overlay for half and full for full!
                            placesLocationResultLauncher.launch(intent)
                        }
                    )
                },
                onCurrentLocationClicked = {

                    if (isLocationEnabled(localContext)){
                        handleLocationClick(
                            localContext,
                            onPermissionRequired = {
                                locationPermissionsResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                locationPermissionsResultLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                            },
                            onPermissionsGranted = {
                                Toast.makeText(localContext, "Current location updating", Toast.LENGTH_SHORT).show()
                                editAndDeleteProduceViewModel.isLocationPickerDialogVisible(false)
                                fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                                    .addOnSuccessListener { location ->
                                        editAndDeleteProduceViewModel.onLatLongChanged(LatLng(location.latitude, location.longitude))
                                        editAndDeleteProduceViewModel.getAddressFromLocation(location,geoCoder)
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.e("LocationRetrived", "Error getting location", exception)
                                    }
                            }
                        )
                    } else{
                        editAndDeleteProduceViewModel.onLocationVisibleDialogChanged(true)
                    }
                },
                onEditButtonClicked = {
                    editAndDeleteProduceViewModel.isEditConfirmDialogVisible(true)
                },
                onDeleteButtonClicked = {
                    editAndDeleteProduceViewModel.isDeleteConfirmDialogVisible(true)
                }
            )
        }
    )
}

@Preview
@Composable
private fun EditAndDeleteMyProduceScreenPreview() {
    EditAndDeleteMyProduceScreen(navController = null)
}