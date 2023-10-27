package com.example.myfamily

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    // arrray of  permissions
    val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_CONTACTS
    )
    val permissionCode = 22
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // fun for asking permissions in app
        askForPermission()

        // bottom bar
        val bottomBar = findViewById<BottomNavigationView>(R.id.bottomBar)
        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_guard -> {

                    inflateFragment(GuardFragment.newInstance())
                }

                R.id.nav_home -> {

                    inflateFragment(HomeFragment.newInstance())
                }

                R.id.nav_dashboard -> {

                    inflateFragment(MapsFragment())
                }

                else -> {
                    inflateFragment(ProfileFragment.newInstance())
                }
            }

            true
        }
        bottomBar.selectedItemId = R.id.nav_home
    }

    // fun for asking permissions in app
    private fun askForPermission() {
        ActivityCompat.requestPermissions(this, permissions, permissionCode)
    }

    private fun inflateFragment(newInstance: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newInstance)
        transaction.commit()
    }

    // on request permission launch result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionCode) {
            if (allPermissionGranted()) {
                // opening camera
               // openCamera()
            } else {

            }
        }
    }

    // opening camera fun
    private fun openCamera() {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        startActivity(intent)
    }

    // fun checking if all the permission are granted or not
    private fun allPermissionGranted(): Boolean {
        for (item in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    item
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }


}