package com.kttq.attendassist.core.ble.advertiser

import android.annotation.SuppressLint
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.BluetoothLeAdvertiser
import android.os.Parcel
import android.os.ParcelUuid
import android.util.Log
import jakarta.inject.Inject
import java.util.UUID

class BleAdvertiserImpl @Inject constructor(
    private val bleAdvertiser: BluetoothLeAdvertiser?,
    private val defaultAdvertiseSetting: AdvertiseSettings,
    private val serviceUuid: UUID
) : BleAdvertiser {

    private var advertisingState = false
    private var advertiseCallback: AdvertiseCallback? = null

    override fun isAdvertising(): Boolean = advertisingState

    @SuppressLint("MissingPermission")
    override fun stopAdvertising() {
        if (bleAdvertiser == null || advertiseCallback == null) {
            advertisingState = false
            return
        }

        if (!advertisingState) return

        bleAdvertiser.stopAdvertising(advertiseCallback)
        advertisingState = false
        advertiseCallback = null
    }

    @SuppressLint("MissingPermission")
    override fun startAdvertising(
        data: ByteArray,
        advertiseSettings: AdvertiseSettings?,
        onSuccess: () -> Unit,
        onFail: (errorCode: Int) -> Unit
    ) {
        if (bleAdvertiser == null) {
            onFail(AdvertiseCallback.ADVERTISE_FAILED_INTERNAL_ERROR)
            advertisingState = false
            return
        }

        if (advertisingState) {
            onFail(AdvertiseCallback.ADVERTISE_FAILED_ALREADY_STARTED)
            return
        }

        val parcelServiceUuid = ParcelUuid(serviceUuid)
        val advertiseData = AdvertiseData.Builder()
            .setIncludeDeviceName(false)
            .addServiceData(parcelServiceUuid,data)
            .build()


        Log.d("BleAdvertiserImpl", "data bytes = ${data.size}")

        val settings = advertiseSettings ?: defaultAdvertiseSetting

        advertiseCallback = object : AdvertiseCallback() {
            override fun onStartFailure(errorCode: Int) {
                super.onStartFailure(errorCode)
                onFail(errorCode)
                val error: String = when (errorCode) {
                    ADVERTISE_FAILED_ALREADY_STARTED -> "already advertising."
                    ADVERTISE_FAILED_DATA_TOO_LARGE -> "payload > 31 bytes."
                    ADVERTISE_FAILED_FEATURE_UNSUPPORTED -> "feature not supported."
                    ADVERTISE_FAILED_INTERNAL_ERROR -> "internal Bluetooth stack error."
                    ADVERTISE_FAILED_TOO_MANY_ADVERTISERS -> "no advertising instances available."
                    else -> "Unknown error."
                }
                Log.d("BleAdvertiserImpl", "start failed: $error")
                advertisingState = false
                advertiseCallback = null
            }

            override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
                Log.d("BleAdvertiserImpl", "start success")
                super.onStartSuccess(settingsInEffect)
                onSuccess()
                advertisingState = true
            }
        }

        bleAdvertiser.startAdvertising(
            settings,
            advertiseData,
            advertiseCallback
        )
        Log.d("BleAdvertiserImpl", "start advertising")
    }
}


