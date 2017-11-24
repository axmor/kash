package com.axmor.ipc_sample

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.axmor.ipc.IPCObserver
import com.axmor.ipc_sample.tracking.TrackingService
import com.axmor.ipc_sample.tracking.ipc.ITrackingService
import com.axmor.ipc_sample.tracking.ipc.TrackingServiceClient


class MainActivity : AppCompatActivity() {

    private var service: ITrackingService? = null


    private val trackingConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName,
                                        serviceBinder: IBinder) {

            Log.d(this.javaClass.simpleName, "Tracking Service Connected")

            service = TrackingServiceClient(serviceBinder, Looper.getMainLooper())
            registerServiceStateObserver()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d(this.javaClass.simpleName, "Tracking Service Disconnected")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val serviceIntent = Intent(this, TrackingService::class.java)
        bindService(serviceIntent, trackingConnection, Context.BIND_AUTO_CREATE)
    }


    private fun registerServiceStateObserver() {
        service?.getStateObservable()?.addObserver(object : IPCObserver<TrackingService.PublicState> {
            override fun onChanged(value: TrackingService.PublicState) {
                var s = value.state
            }
        })
    }


}
