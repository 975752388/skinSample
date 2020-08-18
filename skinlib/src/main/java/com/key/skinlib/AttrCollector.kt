package com.key.skinlib

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.key.skinlib.utils.SkinUtil

/**
 * @author key
 * @description:
 * @date :2020/8/15 15:09
 */
class AttrCollector {
    private val attrs = listOf("background","src","textColor","drawableLeft","drawableTop","drawableRight","drawableBottom")
    private val skinViews = ArrayList<SkinView>()

    fun  collectionAttr(view: View,attributeSet: AttributeSet){
        val skinPairs = ArrayList<Pair<String,Int>>()
        for(i in 0 until attributeSet.attributeCount){
            val attrName = attributeSet.getAttributeName(i)
            if (attrs.contains(attrName)){
               val attrValue =  attributeSet.getAttributeValue(i)
                if (attrValue.startsWith("#")){
                    continue
                }

                var resId = if (attrValue.startsWith("?")){
                    val attrId = attrValue.substring(1).toInt()
                    SkinUtil.getResId(view.context, intArrayOf(attrId))[0]
                }else{//以@开头  @color/xxx  @drawable/xxx
                    attrValue.substring(1).toInt()
                }
                val pair = Pair<String,Int>(attrName,resId)
                skinPairs.add(pair)
            }
        }
        if (skinPairs.isNotEmpty() || view is ISkinableView) {
            val skinView = SkinView(view, skinPairs)
            skinView.changeSkin()
            skinViews.add(skinView)
        }
    }
    fun changeSkin(){
        skinViews.forEach {
            it.changeSkin()
        }
    }


    class SkinView(private val view:View,private val skinPairs:List<Pair<String,Int>>){

        fun changeSkin(){
            if (view is ISkinableView){
                (view as ISkinableView).changeSkin()
            }

            for (skinPair in skinPairs) {
                var left: Drawable? = null
                var top: Drawable? = null
                var right: Drawable? = null
                var bottom: Drawable? = null
                when(skinPair.first){
                    "background"->{
                        val background = SkinResource.getInstance().getBackground(skinPair.second)
                        if (background is Int) {
                            view.setBackgroundColor(background)
                        } else {
                            ViewCompat.setBackground(view, background as Drawable
                            )
                        }
                    }

                    "src"->{
                        val background = SkinResource.getInstance().getBackground(skinPair.second)
                        (view as ImageView).setImageDrawable(ColorDrawable(background as Int))
                    }
                    "textColor"->{
                        (view as TextView).setTextColor(SkinResource.getInstance().getColorStateList(skinPair.second))
                    }
                    "drawableLeft"->{
                        left = SkinResource.getInstance().getDrawable(skinPair.second)
                    }
                    "drawableTop"->{
                       top = SkinResource.getInstance().getDrawable(skinPair.second)
                    }
                    "drawableRight"->{
                        right = SkinResource.getInstance().getDrawable(skinPair.second)
                    }
                    "drawableBottom"->{
                        bottom = SkinResource.getInstance().getDrawable(skinPair.second)
                    }
                }
                if (null != left || null != right || null != top || null != bottom) {
                    (view as TextView).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom
                    )
                }

            }
        }
    }
}