package com.camerax.databindingdemo

data class Student(val name:String, val age:Int){

    fun toAgeStr(): String{
        return ""+age
    }
}