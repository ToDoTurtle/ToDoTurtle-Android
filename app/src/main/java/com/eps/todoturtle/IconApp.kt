package com.eps.todoturtle

import android.app.Application
import com.maltaisn.icondialog.pack.IconPack
import com.maltaisn.icondialog.pack.IconPackLoader
import com.maltaisn.iconpack.defaultpack.createDefaultIconPack

class IconApp : Application() {

    var iconPack: IconPack? = null

    override fun onCreate() {
        super.onCreate()
        loadIconPack()
    }

    private fun loadIconPack() {
        val loader = IconPackLoader(this)
        val iconPack = createDefaultIconPack(loader)
        iconPack.icons
        iconPack.loadDrawables(loader.drawableLoader)
        this.iconPack = iconPack
    }
}
