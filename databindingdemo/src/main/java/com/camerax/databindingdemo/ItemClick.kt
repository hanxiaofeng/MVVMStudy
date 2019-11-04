package com.camerax.databindingdemo

import android.view.View

interface ItemClick {

    fun itemClick(view: View, book: Book, position: Int)
}