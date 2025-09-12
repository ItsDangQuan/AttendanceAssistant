package com.kttq.attendassist.core.ble.advertiser

import android.bluetooth.le.AdvertiseSettings

interface BleAdvertiser {

    /**
     * Advertising failure codes (for use with the parameter `errorCode` of the callback onFail).
     *
     * - ADVERTISE_FAILED_ALREADY_STARTED: already advertising.
     * - ADVERTISE_FAILED_DATA_TOO_LARGE: payload > 31 bytes.
     * - ADVERTISE_FAILED_FEATURE_UNSUPPORTED: feature not supported.
     * - ADVERTISE_FAILED_INTERNAL_ERROR: internal Bluetooth stack error.
     * - ADVERTISE_FAILED_TOO_MANY_ADVERTISERS: no advertising instances available.
     */
    fun startAdvertising(
        data: ByteArray,
        advertiseSettings: AdvertiseSettings? = null,
        onSuccess: () -> Unit = {},
        onFail: (errorCode: Int) -> Unit = {},
    )
    fun stopAdvertising()
    fun isAdvertising(): Boolean
}