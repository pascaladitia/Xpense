package com.pascal.xpense.utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDate
import platform.Foundation.NSOperationQueue
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
