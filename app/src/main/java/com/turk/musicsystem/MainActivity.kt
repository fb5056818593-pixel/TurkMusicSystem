package com.turk.musicsystem

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val BLUETOOTH_PERMISSION_CODE = 100
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        
        setupMainButtons()
        setupSettingsButtons()
    }

    private fun setupMainButtons() {
        findViewById<Button>(R.id.btnRadio).setOnClickListener {
            startActivity(Intent(this, RadioPlayerActivity::class.java))
        }
        findViewById<Button>(R.id.btnMusic).setOnClickListener {
            startActivity(Intent(this, MusicPlayerActivity::class.java))
        }
        findViewById<Button>(R.id.btnVideo).setOnClickListener {
            startActivity(Intent(this, VideoPlayerActivity::class.java))
        }
    }

    private fun setupSettingsButtons() {
        findViewById<ImageButton>(R.id.btnBluetooth).setOnClickListener {
            checkBluetoothPermissions()
        }
        findViewById<ImageButton>(R.id.btnWiFi).setOnClickListener {
            openWiFiSettings()
        }
    }

    private fun checkBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val bluetoothScanPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            )
            val bluetoothConnectPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            )

            if (bluetoothScanPermission != PackageManager.PERMISSION_GRANTED ||
                bluetoothConnectPermission != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ),
                    BLUETOOTH_PERMISSION_CODE
                )
            } else {
                openBluetoothSettings()
            }
        } else {
            openBluetoothSettings()
        }
    }

    private fun openBluetoothSettings() {
        startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
    }

    private fun openWiFiSettings() {
        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == BLUETOOTH_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openBluetoothSettings()
            } else {
                Toast.makeText(this, "Bluetooth izni reddedildi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}