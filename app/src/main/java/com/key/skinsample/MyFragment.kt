package com.key.skinsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * @author key
 * @description:
 * @date :2020/8/18 11:10
 */
class MyFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_my,null,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = arguments?.getString("num")
    }
    companion object{
        fun newInstance(string: String):MyFragment{
            return MyFragment().also {
                it.arguments = Bundle().also {
                    it.putString("num",string)
                }
            }
        }
    }
}