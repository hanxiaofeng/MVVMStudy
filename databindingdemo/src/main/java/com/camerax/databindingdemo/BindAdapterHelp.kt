package com.camerax.databindingdemo

import android.text.Spanned
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter


object BindAdapterHelp  {



    @BindingAdapter("android:textmm")
    @JvmStatic
    fun setTextCommon(view: TextView, text: CharSequence?){
        val oldText = view.text

        if (text === oldText) {
            return
        }
        if (text is Spanned) {
            if (text == oldText) {
                return // No change in the spans, so don't set anything.
            }
        } else if (!haveContentsChanged(text, oldText)) {
            return // No content changes, so don't set anything.
        }
        //下面这句代码，就是我们加进去的
        val upperText = "通用前缀：$text"
        view.text = upperText
    }

    private fun haveContentsChanged(str1: CharSequence?, str2: CharSequence?): Boolean {
        if (str1 == null != (str2 == null)) {
            return true
        } else if (str1 == null) {
            return false
        }
        val length = str1.length
        if (length != str2!!.length) {
            return true
        }
        for (i in 0 until length) {
            if (str1[i] != str2[i]) {
                return true
            }
        }
        return false
    }
}