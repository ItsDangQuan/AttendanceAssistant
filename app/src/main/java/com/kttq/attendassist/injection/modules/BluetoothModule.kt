package com.kttq.attendassist.injection.modules

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.BluetoothLeAdvertiser
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.ParcelUuid
import com.kttq.attendassist.core.ble.advertiser.BleAdvertiser
import com.kttq.attendassist.core.ble.advertiser.BleAdvertiserImpl
import com.kttq.attendassist.core.ble.scanner.BleScanner
import com.kttq.attendassist.core.ble.scanner.BleScannerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.UUID
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BluetoothComponent {
    companion object{
        val SERVICE_UUID: UUID = UUID.fromString("0000F890-0000-1000-8000-00805F9B34FB")
    }
    @Provides
    @Singleton
    fun provideServiceUuid(): UUID {
        return SERVICE_UUID
    }

    @Provides
    @Singleton
    fun provideDefaultScanFilter(
        serviceUuid: UUID
    ): ScanFilter {
        return ScanFilter
            .Builder()
            .setServiceData(ParcelUuid(serviceUuid), byteArrayOf())
            .build()
    }

    @Provides
    @Singleton
    fun provideDefaultScanSettings(): ScanSettings {
        return ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setReportDelay(200)
            .build()
    }

    @Provides
    @Singleton
    fun provideBluetoothAdapter(
        @ApplicationContext appContext: Context
    ): BluetoothAdapter? {
        val bluetoothManager = appContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return bluetoothManager.adapter
    }

    @Provides
    @Singleton
    fun provideBleScanner(
        bluetoothAdapter: BluetoothAdapter?
    ) : BluetoothLeScanner? {
        return bluetoothAdapter?.bluetoothLeScanner
    }

    @Provides
    @Singleton
    fun provideBleAdvertiser(
        bluetoothAdapter: BluetoothAdapter?
    ) : BluetoothLeAdvertiser? {
        return bluetoothAdapter?.bluetoothLeAdvertiser
    }

    @Provides
    @Singleton
    fun provideDefaultAdvertiseSetting(): AdvertiseSettings {
        return AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
            .setConnectable(false)
            .build()
    }


}

@Module
@InstallIn(SingletonComponent::class)
abstract class BleModule {
    @Binds
    @Singleton
    abstract fun bindBleScanner(
        bleScannerImpl: BleScannerImpl
    ): BleScanner

    @Binds
    @Singleton
    abstract fun bindBleAdvertiser(
        bleAdvertiserImpl: BleAdvertiserImpl
    ): BleAdvertiser
}

//TODO: Make it safer.
//You do this, my fen, not me :v