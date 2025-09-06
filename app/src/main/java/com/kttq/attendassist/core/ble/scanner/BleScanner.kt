package com.kttq.attendassist.core.ble.scanner

import android.bluetooth.le.ScanFilter

interface BleScanner {
    /**
     * The string in the first callback representing the data the device receive
     **/
    fun startScan(
        filter: List<ScanFilter>? = null,
        onSuccess: (String) -> Unit,
        onFail: (errorCode: Int) -> Unit = {}
    )
    fun stopScan();
    fun isScanning(): Boolean
}