package com.example.morganseielstad.politicado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.NetworkSecurityPolicy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PolitcianController().get_politicians()
    }
}
