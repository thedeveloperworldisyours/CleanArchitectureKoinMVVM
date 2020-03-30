package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source

import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.PermissionChecker

class AndroidPermissionChecker (private val application: Application) : PermissionChecker {

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        ContextCompat.checkSelfPermission(
            application,
            permission.toAndroidId()
        ) == PackageManager.PERMISSION_GRANTED
}

private fun PermissionChecker.Permission.toAndroidId() = when (this) {
    PermissionChecker.Permission.COARSE_LOCATION -> android.Manifest.permission.ACCESS_COARSE_LOCATION
}