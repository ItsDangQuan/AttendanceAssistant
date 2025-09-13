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

    private var callback: ScanCallback? = null

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override fun stopScan() {
        if (bleScanner == null || !scanningState || callback == null) {
            return
        }
        scanningState = false
        bleScanner.stopScan(callback)
        callback = null
        Log.d("BleScannerImpl", "Stop scanning")
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override fun startScan(
        filter: List<ScanFilter>?,
        onSuccess: (ByteArray) -> Boolean,
        onFail: (errorCode: Int) -> Unit
    ) {
         callback = object : ScanCallback() {
            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                onFail(errorCode)
                Log.d("BleScannerImpl", "onScanFailed: $errorCode")
                scanningState = false
            }

            @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)
                Log.d("BleScannerImpl", "onScanResult: ${result.toString()}")
                val data = result.scanRecord?.serviceData?.values?.firstOrNull() ?: return
                if (onSuccess(data)) {
                    bleScanner?.stopScan(this)
                    Log.d("BleScannerImpl", "Stop scanning")
                    scanningState = false
                }
            }

             @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
             override fun onBatchScanResults(results: List<ScanResult>) {
                 super.onBatchScanResults(results)
                 Log.d("BleScannerImpl", "onBatchScanResults: ${results.toString()}")
                 val data = results.firstOrNull()?.scanRecord?.serviceData?.values?.firstOrNull() ?: return
                 if (onSuccess(data)) {
                     bleScanner?.stopScan(this)
                     Log.d("BleScannerImpl", "Stop scanning")
                     scanningState = false
                 }
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