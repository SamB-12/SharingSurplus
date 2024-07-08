package com.example.sharingsurplus.presentation.utils

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDenied: Boolean): String
}