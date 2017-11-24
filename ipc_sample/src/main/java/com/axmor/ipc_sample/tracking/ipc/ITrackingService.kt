package com.axmor.ipc_sample.tracking.ipc

import com.axmor.ipc.IPCObservable
import com.axmor.ipc_sample.tracking.TrackingService

/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
interface ITrackingService {
    fun getStateObservable() : IPCObservable<TrackingService.PublicState>
}
