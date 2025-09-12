package com.kttq.attendassist.core.ble.scanner

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.util.Log
import androidx.annotation.RequiresPermission
import jakarta.inject.Inject

//TODO: Checking permission
class BleScannerImpl @Inject constructor(
    private val bleScanner: BluetoothLeScanner?,
    private val defaultSettings: ScanSettings,
    private val defaultFilter: ScanFilter
): BleScanner {
    private var scanningState = false
    override fun isScanning(): Boolean = scanningState

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
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

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override fun startScan(
        filter: List<ScanFilter>?,
        onSuccess: (ByteArray) -> Unit,
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
                Log.d("BleScannerImpl", "onScanResult: ${result.toString()}")
                val data = result?.scanRecord?.serviceData?.values?.firstOrNull() ?: return
                onSuccess(data)
                scanningState = false
            }
        }
        val combinedFilters: List<ScanFilter> = (filter.orEmpty() + defaultFilter)
        bleScanner?.startScan(
            combinedFilters,
            defaultSettings,
            callback
        )
        Log.d("BleScannerImpl", "Start scanning")
    }

}