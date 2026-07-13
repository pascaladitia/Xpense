package com.pascal.xpense.utils

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
actual fun rememberCameraCapture(
    onResult: (ByteArray?, String) -> Unit,
): CameraCaptureState {
    val context = LocalContext.current

    val tempUriState = remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            val uri   = tempUriState.value
            val bytes = uri?.let {
                context.contentResolver.openInputStream(it)?.use { s -> s.readBytes() }
            }
            val name  = "photo_${System.currentTimeMillis()}.jpg"
            onResult(bytes, name)
        } else {
            onResult(null, "")
        }
        tempUriState.value = null
    }

    return remember {
        object : CameraCaptureState {
            override fun launch() {
                val file = createTempImageFile(context)
                val uri  = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file,
                )
                tempUriState.value = uri
                launcher.launch(uri)
            }
        }
    }
}

@Composable
actual fun rememberImagePicker(
    onResult: (ByteArray?, String) -> Unit,
): ImagePickerState {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val bytes = context.contentResolver
                .openInputStream(uri)?.use { it.readBytes() }
            val name  = uri.lastPathSegment ?: "image_${System.currentTimeMillis()}.jpg"
            onResult(bytes, name)
        } else {
            onResult(null, "")
        }
    }

    return remember {
        object : ImagePickerState {
            override fun launch() = launcher.launch("image/*")
        }
    }
}

private fun createTempImageFile(context: Context): File {
    val ts  = SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(Date())
    val dir = context.getExternalFilesDir("Pictures") ?: context.filesDir
    dir.mkdirs()
    return File.createTempFile("IMG_${ts}_", ".jpg", dir)
}