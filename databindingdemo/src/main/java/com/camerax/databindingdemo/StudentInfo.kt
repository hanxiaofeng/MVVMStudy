package com.camerax.databindingdemo

import android.widget.TextView
import androidx.databinding.*
import androidx.databinding.library.baseAdapters.BR
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import android.text.Spanned



class StudentInfo : BaseObservable() {

    @get:Bindable
    var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var age: Int = 0
    set(value) {
        field = value
        notifyPropertyChanged(BR.age)
    }

    var school: ObservableField<String>? = null


    var mList= ObservableArrayList<String>()

    @get:Bindable
    var inputName: String = ""
    set(value) {
        field = value
        notifyPropertyChanged(BR.inputName)
    }
}