package com.kttq.attendassist.core.ble.scanner

import android.bluetooth.le.ScanFilter

/**
 * BLE Scan Failure Codes:
 *
 * - SCAN_FAILED_ALREADY_STARTED: Scan with same settings already running.
 * - SCAN_FAILED_APPLICATION_REGISTRATION_FAILED: App failed to register for scan.
 * - SCAN_FAILED_FEATURE_UNSUPPORTED: Requested scan feature not supported.
 * - SCAN_FAILED_INTERNAL_ERROR: Internal Bluetooth system error.
 * - SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES: No available hardware resources.
 * - SCAN_FAILED_SCANNING_TOO_FREQUENTLY: Scanning started too often.
 */

interface BleScanner {
    /**
     * The string in the first callback representing the data the device receive
     **/
    fun startScan(
        filter: List<ScanFilter>? = null,
        onSuccess: (String) -> Unit,
        onFail: (errorCode: Int) -> Unit = {}
    )
    fun stopScan()
    fun isScanning(): Boolean
}