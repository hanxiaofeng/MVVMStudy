package com.camerax.databindingdemo.viewmodel

import android.content.Context
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue
import android.net.ConnectivityManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.IntentFilter


class NetWorkLiveData(context: Context) : LiveData<NetworkInfo>() {

    private var mContext: Context = context.applicationContext

    private var mNetworkReceiver: NetworkReceiver? = null
    private var mIntentFilter: IntentFilter? = null

    init {
        mNetworkReceiver = NetworkReceiver()
        mIntentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    }

    companion object {
        @Volatile
        var mNetworkLiveData: NetWorkLiveData? = null

        fun getInstance(mContext: Context): NetWorkLiveData {
            if (mNetworkLiveData == null) {
                synchronized(NetWorkLiveData::class) {
                    if (mNetworkLiveData == null) {
                        mNetworkLiveData = NetWorkLiveData(mContext)
                    }
                }
            }
            return mNetworkLiveData!!
        }
    }

    override fun onActive() {
        super.onActive()
        mContext.registerReceiver(mNetworkReceiver, mIntentFilter)
    }

    override fun onInactive() {
        super.onInactive()
        mContext.unregisterReceiver(mNetworkReceiver)
    }

    private class NetworkReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val manager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = manager.activeNetworkInfo
            NetWorkLiveData.getInstance(context).value = activeNetwork
        }
    }

}