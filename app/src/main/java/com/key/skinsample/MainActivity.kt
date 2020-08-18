package com.key.skinsample

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.key.skinlib.SkinEngine
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("==","MainActivity super oncreate before")
        super.onCreate(savedInstanceState)
        Log.e("==","MainActivity super oncreate after")
        setContentView(R.layout.activity_main)
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),100)
        button.setOnClickListener {
            SkinEngine.getInstance().loadSkin(Environment.getExternalStorageDirectory().path+"/app.apk")

        }
        button2.setOnClickListener { SkinEngine.getInstance().loadSkin(null) }

        viewpager.adapter = object :FragmentPagerAdapter(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
            override fun getItem(position: Int): Fragment {
                return MyFragment.newInstance(position.toString())
            }

            override fun getCount(): Int {
                return 3
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return when(position){
                    0-> "1111"
                    1-> "2222"
                    2-> "3333"
                    else ->""

                }
            }
        }
        tab.setupWithViewPager(viewpager)

    }



}