package com.key.skinlib.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * @author key
 * @description:
 * @date :2020/8/17 10:14
 */
class SpUtils private constructor(context: Context){
    private val sp:SharedPreferences = context.getSharedPreferences("skin_sp",Context.MODE_PRIVATE)

    companion object{
        private const val skinPathKey="skin_path_key"
        private var instance:SpUtils?=null
        fun getInstance(context: Context):SpUtils{
            if (instance==null){
                instance = SpUtils(context)
            }
            return instance!!
        }
    }
    fun setSkin(path:String){
        sp.edit().putString(skinPathKey,path).apply()
    }
    fun getSkinPath():String{
        return sp.getString(skinPathKey,"")!!
    }

    fun resetSkin(){
        sp.edit().remove(skinPathKey).apply()
    }

}