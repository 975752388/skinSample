package com.key.skinsample

import android.content.Context

import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout
import com.key.skinlib.ISkinableView
import com.key.skinlib.SkinResource


/**
 * @author key
 * @description:
 * @date :2020/8/18 14:11
 */
class CustomTabLayout(context: Context,attr:AttributeSet?,defStyleAttr:Int):TabLayout(context,attr,defStyleAttr),ISkinableView {
    constructor(context: Context,attr:AttributeSet?):this(context,attr,0)
    constructor(context: Context):this(context,null,0)
    private var tabTextColorResId = 0


    init {
       val typedArray =  context.obtainStyledAttributes(attr,R.styleable.TabLayout,defStyleAttr,0)
       tabTextColorResId =  typedArray.getResourceId(R.styleable.TabLayout_tabTextColor,0)
        //typedArray.getResourceId(R.styleable.TabLayout_tabSelectedTextColor,0)
        typedArray.recycle()
    }
    override fun changeSkin() {
        if (tabTextColorResId !=0){
            tabTextColors = SkinResource.getInstance().getColorStateList(tabTextColorResId)
        }
    }
}