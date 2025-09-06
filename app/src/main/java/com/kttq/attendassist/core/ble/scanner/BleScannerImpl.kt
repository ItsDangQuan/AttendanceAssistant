package com.kttq.attendassist.core.ble.scanner

import android.annotation.SuppressLint
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import jakarta.inject.Inject

//TODO: Checking permission
class BleScannerImpl @Inject constructor(
    private val bleScanner: BluetoothLeScanner?,
    private val defaultSettings: ScanSettings,
    private val defaultFilter: ScanFilter
): BleScanner {
    private var scanningState = false
    override fun isScanning(): Boolean = scanningState

    @SuppressLint("MissingPermission") // Permissions should be checked by the caller
    override fun stopScan() {
        if (bleScanner == null) {
            return
        }
        // TODO: Handle the callback even the scanning is stopped
        val callback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
            }
            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
            }
        }
        bleScanner.stopScan(callback)
    }

    @SuppressLint("MissingPermission") // Permissions should be checked by the caller
    override fun startScan(
        filter: List<ScanFilter>?,
        onSuccess: (String) -> Unit,
        onFail: (errorCode: Int) -> Unit
    ) {
        val callback = object : ScanCallback() {
            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                onFail(errorCode)
                scanningState = false
            }
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)

                val data = result?.scanRecord?.serviceData
                onSuccess(data.toString())
                scanningState = false
            }
        }
        val combinedFilters: List<ScanFilter> = (filter.orEmpty() + defaultFilter)
        bleScanner?.startScan(
            combinedFilters,
            defaultSettings,
            callback
        )
    }

}