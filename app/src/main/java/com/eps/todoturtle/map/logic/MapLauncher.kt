package com.eps.todoturtle.map.logic

import android.content.Intent
import android.net.Uri

private const val URI_TEMPLATE = "geo:0,0?q=%f,%f"
private const val PACKAGE = "com.google.android.apps.maps"


object MapLauncher {
    fun getMapIntent(latitude: Double, longitude: Double): Intent {
        val uri = getUriFrom(latitude, longitude)
        val intent = getIntentFrom(uri)
        setPackageTo(intent)
        return intent
    }

    private fun getUriFrom(latitude: Double, longitude: Double) =
        URI_TEMPLATE.format(latitude, longitude)

    private fun getIntentFrom(uri: String) =
        Intent(Intent.ACTION_VIEW, Uri.parse(uri))

    private fun setPackageTo(intent: Intent) =
        intent.setPackage(PACKAGE)
}