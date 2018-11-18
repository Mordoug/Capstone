package com.example.morganseielstad.politicado

import android.util.Log
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PolitcianController {
    var politicians = listOf<Politician>()



    fun get_politicians(){
        FuelManager.instance.basePath = "http:/10.0.2.2:8008/api"
        "/Politicians".httpGet().responseString { request, response, result ->
            //make a GET to https://httpbin.org/get and do something with response
            val (data, error) = result
            if (error == null) {
                //do something when success
                val list = Gson.fromJson<String>("Success" , String)
                val politicianType = object : TypeToken<List<Politician>>() {}.type
                politicians = Gson().fromJson<List<Politician>>(result, politicianType)
            } else {
                //error handling
                Log.i("Json", error.toString())
            }
        }
    }
}
