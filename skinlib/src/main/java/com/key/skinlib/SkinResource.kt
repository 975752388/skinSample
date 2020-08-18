package com.key.skinlib

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.TextUtils

/**
 * @author key
 * @description:
 * @date :2020/8/15 18:05
 */
class SkinResource private constructor(context: Context) {
    private var mSkinPackageName: String? = null
    private var isDefaultSkin = true // 默认情况下，可以恢复默认的皮肤


    // app壳本身原生的 Resources
    private var mAppResources: Resources? = null

    // 皮肤包外界加载专用的 Resources
    private var mSkinResources: Resources? = null
    init {
        mAppResources = context.resources
    }
    companion object{
        @Volatile
        private var instance:SkinResource?=null
        fun init(context: Context){
            if (instance == null){
                synchronized(SkinResource::class.java){
                    if (instance == null){
                        instance = SkinResource(context)
                    }
                }
            }
        }

        fun getInstance() = instance!!


    }

    // 换肤的准备工作
    fun applySkinAction(resources: Resources?, packageName: String?) {
        mSkinResources = resources
        mSkinPackageName = packageName

        isDefaultSkin = TextUtils.isEmpty(packageName) || resources == null
    }

    // 恢复默认的准备工作
    fun resetSkinAction() {
        mSkinResources = null
        mSkinPackageName = ""
        isDefaultSkin = true // 一旦是true，就会恢复默认
    }

    /**
     * 1.通过原始app中的resId(R.color.XX)获取到自己的 名字
     * 2.根据名字和类型获取皮肤包中的ID
     */
    fun getIdentifier(resId: Int): Int {
        if (isDefaultSkin) {
            return resId
        }
        val resName = mAppResources!!.getResourceEntryName(resId)
        val resType = mAppResources!!.getResourceTypeName(resId)
        return mSkinResources!!.getIdentifier(resName, resType, mSkinPackageName)
    }

    /**
     * 输入主APP的ID，到皮肤APK文件中去找到对应ID的颜色值
     * @param resId
     * @return
     */
    fun getColor(resId: Int): Int {
        if (isDefaultSkin) {
            return mAppResources!!.getColor(resId)
        }
        val skinId = getIdentifier(resId)
        return if (skinId == 0) {
            mAppResources!!.getColor(resId)
        } else mSkinResources!!.getColor(skinId)

    }

    fun getColorStateList(resId: Int): ColorStateList? {
        if (isDefaultSkin) {
            return mAppResources!!.getColorStateList(resId)
        }
        val skinId = getIdentifier(resId)
        return if (skinId == 0) {
            mAppResources!!.getColorStateList(resId)
        } else mSkinResources!!.getColorStateList(skinId)
    }

    fun getDrawable(resId: Int): Drawable? {
        if (isDefaultSkin) {
            return mAppResources!!.getDrawable(resId)
        }
        //通过 app的resource 获取id 对应的 资源名 与 资源类型
        //找到 皮肤包 匹配 的 资源名资源类型 的 皮肤包的 资源 ID
        val skinId = getIdentifier(resId)
        return if (skinId == 0) {
            mAppResources!!.getDrawable(resId)
        } else mSkinResources!!.getDrawable(skinId)
    }


    /**
     * 可能是Color 也可能是drawable
     *
     * @return
     */
    fun getBackground(resId: Int): Any? {
        val resourceTypeName = mAppResources!!.getResourceTypeName(resId)
        return if ("color" == resourceTypeName) {
            getColor(resId)
        } else {
            // drawable
            getDrawable(resId)
        }
    }
}