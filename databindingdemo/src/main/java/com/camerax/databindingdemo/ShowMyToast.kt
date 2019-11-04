package com.camerax.databindingdemo

import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast

class ShowMyToast {

    fun clickMe(view: View){
        Log.e("wangkeke","------------------点击了我啊")
    }

    fun clickLongMe(view: View):Boolean{
        Log.e("wangkeke","------------------长按了我啊")
        //长按事件返回值为boolean值，返回值要一致
        return true
    }
}