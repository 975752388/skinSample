package com.key.skinlib

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log
import com.key.skinlib.utils.SpUtils
import java.lang.Exception
import java.util.*

/**
 * @author key
 * @description:
 * @date :2020/8/15 16:34
 */
class SkinEngine private constructor(private val context: Context): Observable() {

    companion object{
        @Volatile
        private var instance:SkinEngine?=null
        fun init(context: Context){
            if (instance == null){
                synchronized(SkinEngine::class.java){
                    if (instance == null){
                        instance = SkinEngine(context)
                    }
                }
            }
        }
        fun  getInstance()= instance!!
    }

    init {
        SkinResource.init(context)
        loadSkin(SpUtils.getInstance(context).getSkinPath())
    }

    fun loadSkin(path:String?){
        if (path.isNullOrEmpty()){
            SpUtils.getInstance(context).resetSkin()
            SkinResource.getInstance().resetSkinAction()
        }else{
            try {
            val appResource = context.resources
            val assetManager = AssetManager::class.java.newInstance()
            val method = assetManager.javaClass.getMethod("addAssetPath",String::class.java)
            method.invoke(assetManager,path)
            val skinResources = Resources(assetManager,appResource.displayMetrics,appResource.configuration)
            val packageName = context.packageManager.getPackageArchiveInfo(path,PackageManager.GET_ACTIVITIES)?.packageName
            SkinResource.getInstance().applySkinAction(skinResources,packageName)
            SpUtils.getInstance(context).setSkin(path)
            }catch (e:Exception){
                Log.e("error","$e")
            }
        }
        setChanged()
        notifyObservers()
    }




}