package com.pascal.xpense.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSDate
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSUserDomainMask
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

actual fun showToast(msg: String) {
    val alert = UIAlertController.alertControllerWithTitle(
        title = null,
        message = msg,
        preferredStyle = UIAlertControllerStyleAlert
    )
    alert.addAction(
        UIAlertAction.actionWithTitle(
            title = "OK",
            style = UIAlertActionStyleDefault,
            handler = null
        )
    )

    val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootVC?.presentViewController(alert, animated = true, completion = null)
}

actual fun actionShareUrl(url: String?) {
    if (url.isNullOrEmpty()) return

    val items: List<Any> = listOf(url)

    val activityVC = UIActivityViewController(
        activityItems = items,
        applicationActivities = null
    )

    val rootVC: UIViewController =
        UIApplication.sharedApplication.keyWindow?.rootViewController ?: return

    NSOperationQueue.mainQueue.addOperationWithBlock {
        rootVC.presentViewController(activityVC, animated = true, completion = null)
    }
}

@OptIn(ExperimentalForeignApi::class)
actual fun currentTimeMillis(): Long {
    return (NSDate().timeIntervalSince1970 * 1000).toLong()
}

@OptIn(ExperimentalForeignApi::class)
actual fun saveImageBytesToFile(bytes: ByteArray, name: String): String? {
    return try {
        val documentsDir = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )?.path ?: return null
        val attachmentsDir = "$documentsDir/attachments"
        NSFileManager.defaultManager.createDirectoryAtPath(
            path = attachmentsDir,
            withIntermediateDirectories = true,
            attributes = null,
            error = null
        )
        val filePath = "$attachmentsDir/$name"
        val success = bytes.usePinned { pinned ->
            val nsData = NSData(
                bytes = pinned.addressOf(0),
                length = bytes.size.toULong()
            )
            nsData.writeToFile(filePath, atomically = true)
        }
        if (success) filePath else null
    } catch (_: Exception) {
        null
    }
}
