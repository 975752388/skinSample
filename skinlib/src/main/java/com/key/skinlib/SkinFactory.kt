package com.key.skinlib

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import java.lang.reflect.Constructor
import java.util.*

/**
 * @author key
 * @description:
 * @date :2020/8/15 14:16
 */
class SkinFactory(val activity: Activity, private val delegate:AppCompatDelegate):LayoutInflater.Factory2,Observer {
    private val attrCollector = AttrCollector()
    override fun onCreateView(parent: View?, name: String, context: Context, attr: AttributeSet): View? {
        Log.e("==","start collect")
        var view = delegate.createView(parent,name,context,attr)
        if (view == null){//说明不是textview  button imageview等控件
            view = createView(name, context, attr)
        }
        view?.let {
            attrCollector.collectionAttr(view,attr)
        }
        return view
    }

    override fun onCreateView(name: String, context: Context, attr: AttributeSet): View? {
        return null
    }
    private fun createView(name: String, context: Context, attr: AttributeSet):View?{
        var view:View?=null
        if (-1==name.indexOf(".")){//系统控件 如Linearlayout等
            for (i in classPrefix.indices){
               view = tryCreateView(context, classPrefix[i]+name, attr)
            }
        }else{
            view = tryCreateView(context, name, attr)
        }
        return view
    }
    private fun tryCreateView(context: Context,name: String,attr: AttributeSet):View?{
        val constructor = findConstructor(context, name)
        try {
            return constructor?.newInstance(context,attr)
        }catch (e:java.lang.Exception){

        }
        return null
    }
    private fun findConstructor(context: Context, name: String): Constructor<out View?>? {
        var constructor = mConstructorMap[name]
        if (constructor == null) {
            try {
                val clazz =context.classLoader.loadClass(name).asSubclass(View::class.java)
                constructor =clazz.getConstructor(Context::class.java, AttributeSet::class.java)
                mConstructorMap[name] = constructor
            } catch (e: Exception) {
            }
        }
        return constructor
    }

    override fun update(p0: Observable?, p1: Any?) {
        attrCollector.changeSkin()
    }

    companion object{
         val classPrefix = arrayOf("android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view.")
        var mConstructorMap =
            HashMap<String, Constructor<out View?>>()

    }
}