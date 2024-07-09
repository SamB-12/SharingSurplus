package com.example.sharingsurplus.presentation.ui.dashboard.produce.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.activity.ComponentActivity
import androidx.activity.result.PickVisualMediaRequest
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.components.PermissionDialogComponent
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels.AddProduceViewModel
import com.example.sharingsurplus.presentation.utils.CameraPermissionTextProvider
import com.example.sharingsurplus.presentation.utils.GalleryPermissionTextProvider
import com.example.sharingsurplus.presentation.utils.LocationPermissionTextProvider
import com.example.sharingsurplus.presentation.utils.createImageFile
import com.example.sharingsurplus.presentation.utils.createImageUri
import com.example.sharingsurplus.presentation.utils.getUriForFile
import com.example.sharingsurplus.presentation.utils.handleCameraClick
import com.example.sharingsurplus.presentation.utils.handleGalleryClick
import com.example.sharingsurplus.presentation.utils.handleLocationClick
import com.example.sharingsurplus.presentation.utils.isFileExistsAtUri
import com.example.sharingsurplus.presentation.utils.openAppSettings
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.net.URI

@Composable
fun AddProduceScreen(
    modifier: Modifier = Modifier,
    addProduceViewModel: AddProduceViewModel = hiltViewModel(),
    navController: NavHostController?
) {

    val permissionsToRequest = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri?>(Uri.EMPTY)
    }

    val uiState by addProduceViewModel.addProduceUiState.collectAsState()

    val localContext = LocalContext.current
    val activity = (localContext as? Activity)

    val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)//This is what we get from places api

    val multiplePermissionsResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {permissions ->
        permissionsToRequest.forEach { permission ->
            addProduceViewModel.onPermissionResult(
                permission = permission,
                isGranted = permissions[permission] == true
            )
        }
    }

    val cameraPermissionsResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted ->
        addProduceViewModel.onPermissionResult(permission = Manifest.permission.CAMERA, isGranted = isGranted)
    }

    val locationPermissionsResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted ->
        addProduceViewModel.onPermissionResult(permission = Manifest.permission.ACCESS_FINE_LOCATION, isGranted = isGranted)
        addProduceViewModel.onPermissionResult(permission = Manifest.permission.ACCESS_COARSE_LOCATION, isGranted = isGranted)
    }

    val placesLocationResultLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                val place = Autocomplete.getPlaceFromIntent(intent)
                Log.d("PlaceRetrived", "Place: ${place.name}, ${place.latLng}")
            }
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            Log.d("PlaceRetrived", "Place selection canceled")
        }
    }//This retrives the requested location

    val galleryPermissionsResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted ->
        addProduceViewModel.onPermissionResult(permission = Manifest.permission.READ_MEDIA_IMAGES, isGranted = isGranted)
    }


    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            val uri = addProduceViewModel.addProduceUiState.value.tempImageUri
            uri?.let {
                Toast.makeText(localContext, "Camera capture successful", Toast.LENGTH_SHORT).show()
                addProduceViewModel.onImageSelected(uri.toString())
                addProduceViewModel.onImageSelectedUri(it)
                capturedImageUri = it
                addProduceViewModel.isImagePickerDialogVisible(false)
            }
        } else {
            Toast.makeText(localContext, "Camera capture failed", Toast.LENGTH_SHORT).show()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            addProduceViewModel.onImageSelected(uri.toString())
            addProduceViewModel.onImageSelectedUri(uri)
            capturedImageUri = uri
            Toast.makeText(localContext, "Image selected", Toast.LENGTH_SHORT).show()
            addProduceViewModel.isImagePickerDialogVisible(false)
        } ?: run {
            Toast.makeText(localContext, "Image selection failed", Toast.LENGTH_SHORT).show()
        }
    }//this doesn't require permissions


    val dialogQueue = addProduceViewModel.visiblePermissionDialogQueue

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialogComponent(
                permissionTextProvider = when (permission) {
                   Manifest.permission.CAMERA -> {
                       CameraPermissionTextProvider()
                   }
                    Manifest.permission.READ_MEDIA_IMAGES -> {
                        GalleryPermissionTextProvider()
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
                onDismiss = addProduceViewModel::dismissDialog,
                onOkClick = {
                    addProduceViewModel.dismissDialog()
                    multiplePermissionsResultLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick = {openAppSettings(localContext)}
            )
        }

    ScaffoldComponent(//integrate the vm
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = "Add a produce", onBackClick = {navController?.navigateUp()})},
        content = {
            AddScreenMinor(
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
                    painterResource(id = R.drawable.add_screen_image_placeholder)
                } else {
                    rememberAsyncImagePainter(model = uiState.produceImageUri)
               },
//                produceImage = if (capturedImageUri?.path?.isNotEmpty() == false) {
//                    painterResource(id = R.drawable.add_screen_image_placeholder)
//                } else {
//                       rememberAsyncImagePainter(model = capturedImageUri)
//                },
                onProduceNameChange = addProduceViewModel::onProduceNameChanged,
                onProduceDescriptionChange = addProduceViewModel::onProduceDescriptionChanged,
                onProduceTypeChange = addProduceViewModel::onProduceTypeChanged,
                onQuantityChange = addProduceViewModel::onProduceQuantityChanged,
                onUnitChange = addProduceViewModel::onProduceUnitChanged,
                onLocationChange = addProduceViewModel::onLocationChanged,
                onPickupInstructionsChange = addProduceViewModel::onProducePickupInstructionChanged,
                onBestBeforeDateChange = addProduceViewModel::onProduceBestBeforeDateChanged,
                onIsDatePickerDialogVisibleChange = addProduceViewModel::isDatePickerDialogVisible,
                isImagePickerDialogVisible = uiState.isAddImageDialogVisible,
                isLocationPickerDialogVisible = uiState.isLocationDialogVisible,
                onImagePickerDialogVisibleChange = addProduceViewModel::isImagePickerDialogVisible,
                onLocationPickerDialogVisibleChange = addProduceViewModel::isLocationPickerDialogVisible,
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
                                        //addProduceViewModel.onImageSelectedUri(uri)
                                        addProduceViewModel.onTempImageUriChanged(uri)
                                        //addProduceViewModel.onImageSelected(uri.toString())
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
                    handleLocationClick(
                        localContext,
                        onPermissionRequired = {
                            locationPermissionsResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            locationPermissionsResultLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                        },
                        onPermissionsGranted = {
                            Toast.makeText(localContext, "Current location clicked", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun AddProduceScreenPreview() {
    AddProduceScreen(navController = null)
}

fun Activity.hasPermissions(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this,permission) == PackageManager.PERMISSION_GRANTED
}
