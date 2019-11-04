package com.camerax.databindingdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.*
import androidx.databinding.library.baseAdapters.BR
import com.camerax.databindingdemo.databinding.ActivityMainBinding
import com.camerax.databindingdemo.viewmodel.ViewModelActivity

class MainActivity : AppCompatActivity() ,OnUserClickListener{

    private lateinit var dataBinding: ActivityMainBinding

    private lateinit var student: StudentInfo

    override fun userClick(view: View) {
        Toast.makeText(this,"点我干嘛",Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        dataBinding.click = this


        dataBinding.mainHelp = this
        dataBinding.showClick = ShowMyToast()

        student = StudentInfo()
        student.name = "王可可"
        student.age = 29
        student.inputName = ""
        student.school = ObservableField("南京大学")
        val list = ObservableArrayList<String>()
        list.add("吉林")
        list.add("辽宁")
        student.mList = list
        dataBinding.studentInfo = student

        student.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {

            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(propertyId == BR.name){
                    Log.e("wangkeke","name属性发生改变，已更新！")
                }else if(propertyId == BR.age){
                    Log.e("wangkeke","age属性发生改变，已更新！")
                }
            }
        })

        /*dataBinding.btnChange.setOnClickListener {

            val listNew = ObservableArrayList<String>()
            listNew.add("江苏南京")
            listNew.add("江苏常州")
            student.mList = listNew

            student.name = "张三"
            student.school?.set("清华大学")

            dataBinding.studentInfo = student
        }*/

    }

    fun clickBtn(view: View){

        Log.e("wangkeke", "view = $view")

        val listNew = ObservableArrayList<String>()
        listNew.add("江苏南京")
        listNew.add("江苏常州")
        student.mList = listNew

        student.name = "张三"
        student.school?.set("清华大学")

        dataBinding.studentInfo = student
    }

    fun jumpPage(view: View){
        if(view.id == R.id.btn_jump){
            val intent = Intent()
            //获取intent对象
            intent.setClass(this,RecyclerViewActivity::class.java)
            // 获取class是使用::反射
            startActivity(intent)
        }else if(view.id == R.id.btn_viewModel){
            val intent = Intent()
            //获取intent对象
            intent.setClass(this,ViewModelActivity::class.java)
            // 获取class是使用::反射
            startActivity(intent)
        }
    }
}
