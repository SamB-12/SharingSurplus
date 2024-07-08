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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.components.PermissionDialogComponent
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.produce.viewmodels.AddProduceViewModel
import com.example.sharingsurplus.presentation.utils.CameraPermissionTextProvider
import com.example.sharingsurplus.presentation.utils.GalleryPermissionTextProvider
import com.example.sharingsurplus.presentation.utils.LocationPermissionTextProvider
import com.example.sharingsurplus.presentation.utils.createImageUri
import com.example.sharingsurplus.presentation.utils.handleCameraClick
import com.example.sharingsurplus.presentation.utils.handleGalleryClick
import com.example.sharingsurplus.presentation.utils.isFileExistsAtUri
import com.example.sharingsurplus.presentation.utils.openAppSettings

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

//    val uriOfCamera:Uri by remember {
//        mutableStateOf(null)
//    }

    val uiState by addProduceViewModel.addProduceUiState.collectAsState()

    val localContext = LocalContext.current
    val activity = (localContext as? Activity)

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

    val galleryPermissionsResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted ->
        addProduceViewModel.onPermissionResult(permission = Manifest.permission.READ_MEDIA_IMAGES, isGranted = isGranted)
    }


    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            val uri = addProduceViewModel.addProduceUiState.value.produceImageUri
            uri?.let {
                Toast.makeText(localContext, "Camera capture successful", Toast.LENGTH_SHORT).show()
                addProduceViewModel.onImageSelected(uri.toString())
                addProduceViewModel.onImageSelectedUri(it)
                addProduceViewModel.isImagePickerDialogVisible(false)

                if (isFileExistsAtUri(localContext, it)){
                    Log.d("FILEEXISTS", "FILE EXISTS")
                } else {
                    Log.d("FILEEXISTS", "FILE DOES NOT EXIST")
                }

            }
        } else {
            Toast.makeText(localContext, "Camera capture failed", Toast.LENGTH_SHORT).show()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            addProduceViewModel.onImageSelected(uri.toString())
            addProduceViewModel.onImageSelectedUri(uri)
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
                produceImage = if (uiState.produceImageUri == null) {
                    painterResource(id = R.drawable.add_screen_image_placeholder)
                } else {
                    key(uiState.produceImageUri) {
                        Log.d("URIHEREEE", uiState.produceImageUri.toString())
                        rememberAsyncImagePainter(model = uiState.produceImageUri)
                    }
               },
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
//                                 handleGalleryClick(
//                                     localContext,
//                                     onPermissionRequired = {
////                                         multiplePermissionsResultLauncher.launch(
////                                             arrayOf(
////                                                 Manifest.permission.READ_EXTERNAL_STORAGE
////                                             )
////                                         )
//                                         galleryPermissionsResultLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
//                                     },
//                                     onPermissionsGranted = {
//                                         //Toast.makeText(localContext, "Gallery Clicked", Toast.LENGTH_SHORT).show()
//                                         galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//                                     }
//                                 )
                                 galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },// this is where i should display the permission
                onCameraClick = {
                                handleCameraClick(
                                    localContext,
                                    onPermissionRequired = {
                                        cameraPermissionsResultLauncher.launch(Manifest.permission.CAMERA)
                                },
                                    onPermissionsGranted = {
                                        val uri = createImageUri(localContext)
                                        if (uri != null) {
                                            addProduceViewModel.onImageSelectedUri(uri)
                                            addProduceViewModel.onImageSelected(uri.toString())
                                            Log.d("URI", uri.toString())
                                            cameraLauncher.launch(uri)
                                        } else {
                                            Toast.makeText(localContext, "Error creating URI", Toast.LENGTH_SHORT).show()
                                        }
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
