package com.example.morganseielstad.politicado

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callNetwork()
    }
    private fun callNetwork(){
        FuelManager.instance.basePath = "http:/localhost:9000"
        "/foo".httpGet().responseString { request, response, result ->
            //make a GET to https://httpbin.org/get and do something with response
            val (data, error) = result
            if (error == null) {
                //do something when success
                Log.i("Message Return", data)
            } else {
                //error handling
                Log.e("Get Error:", error.toString())
            }
        }
    }
}
