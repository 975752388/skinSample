package com.key.skinsample

import android.app.Application
import com.key.skinlib.SkinEngine

/**
 * @author key
 * @description:
 * @date :2020/8/17 11:07
 */
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        SkinEngine.init(this)
    }
}