package com.kttq.attendassist.core.ble.advertiser

import android.annotation.SuppressLint
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.BluetoothLeAdvertiser
import android.os.ParcelUuid
import android.util.Log
import jakarta.inject.Inject
import java.util.UUID

class BleAdvertiserImpl @Inject constructor(
    private val bleAdvertiser: BluetoothLeAdvertiser?,
    private val defaultAdvertiseSetting: AdvertiseSettings,
    private val serviceUuid: UUID
) : BleAdvertiser {

    private var advertisingState = false // More explicit name for the state variable

    override fun isAdvertising(): Boolean = advertisingState

    @SuppressLint("MissingPermission") // Permissions should be checked by the caller
    override fun stopAdvertising() {
        if (bleAdvertiser == null) {
            advertisingState = false // Ensure state is false if advertiser is not there
            return
        }

        if (!advertisingState) {
            return
        }

        // TODO: Handle the callback even the scanning is stopped
        val callback = object : AdvertiseCallback() {
            override fun onStartFailure(errorCode: Int) {
                super.onStartFailure(errorCode)
                advertisingState = false
            }

            override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
                super.onStartSuccess(settingsInEffect)
                advertisingState = false
            }
        }
        bleAdvertiser.stopAdvertising(callback)
        // Note: advertisingState is updated in the callback asynchronously.
        // Setting it here immediately might be premature if stop is not guaranteed to succeed.
    }

    @SuppressLint("MissingPermission") // Permissions should be checked by the caller
    override fun startAdvertising(
        data: String,
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
            .addServiceUuid(parcelServiceUuid)
            .addServiceData(parcelServiceUuid, data.toByteArray(Charsets.UTF_8))
            .build()

        val settings = advertiseSettings ?: defaultAdvertiseSetting

        val callback = object : AdvertiseCallback() {
            override fun onStartFailure(errorCode: Int) {
                super.onStartFailure(errorCode)
                onFail(errorCode)
                advertisingState = false
            }

            override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
                super.onStartSuccess(settingsInEffect)
                onSuccess()
                advertisingState = true
            }
        }

        bleAdvertiser.startAdvertising(
            settings,
            advertiseData,
            callback
        )
    }
}
