package com.example.sharingsurplus.presentation.utils

class CameraPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDenied: Boolean): String {
        return if (isPermanentlyDenied) {
            "Camera permission is permanently denied. You can enable it in app settings."
        } else {
            "This app needs the camera permission to post the picture of your produce."
        }
    }

}

class GalleryPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDenied: Boolean): String {
        return if (isPermanentlyDenied) {
            "Gallery permission is permanently denied. You can enable it in app settings."
        } else {
            "This app needs the gallery permission to post the picture of your produce."
        }
    }
}

class LocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDenied: Boolean): String {
        return if (isPermanentlyDenied) {
            "Location permission is permanently denied. You can enable it in app settings."
        } else {
            "This app needs the location permission to post your produce."
        }
    }

}