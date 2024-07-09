package com.example.sharingsurplus.presentation.utils

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

fun handleGalleryClick(
    context: Context,
    onPermissionRequired: () -> Unit,
    onPermissionsGranted: () -> Unit
) {
    val permissionsGranted = hasPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)

    if (permissionsGranted) {
        onPermissionsGranted()
    } else {
        onPermissionRequired()
    }
}

fun handleCameraClick(
    context: Context,
    onPermissionRequired: () -> Unit,
    onPermissionsGranted: () -> Unit
) {
    val permissionsGranted = hasPermission(context, Manifest.permission.CAMERA)

    if (permissionsGranted) {
        onPermissionsGranted()
    } else {
        onPermissionRequired()
    }
}

private fun hasPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}

fun createImageUri(context: Context): Uri? {
    val contentResolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}

fun isFileExistsAtUri(context: Context, uri: Uri): Boolean {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        inputStream?.close()
        inputStream != null
    } catch (e: FileNotFoundException) {
        false
    }
}

fun createImageFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "Surplus_${System.currentTimeMillis()}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}

fun getUriForFile(context: Context, file: File): Uri {
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}