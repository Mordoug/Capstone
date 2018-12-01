package com.example.morganseielstad.politicado

import android.util.JsonReader
import android.util.Log
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Handler
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.StringReader


class PoliticianController {
    var politicians = ArrayList<Politician>()


    fun getPoli(): ArrayList<Politician>{
        return politicians
    }

    fun politicianReader(strReader: StringReader) : ArrayList<Politician>{


        val result = ArrayList<Politician>()

        val reader:JsonReader = JsonReader( strReader)

        reader.beginArray()
        while (reader.hasNext()) {
            var id: Int = -1
            var first: String = ""
            var last: String = ""
            var state: String = ""
            var office: String = ""

            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "id" -> id = reader.nextInt()
                    "firstName" -> first = reader.nextString()
                    "lastName" -> last = reader.nextString()
                    "state" -> state = reader.nextString()
                    "office" -> office = reader.nextString()

                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            val person = Politician(id, first, last, state , office)
            result.add(person)
        }
        reader.endArray()
        return result
    }



    fun callPoliticians() : Boolean{
        var success = false


        FuelManager.instance.basePath = "http:/10.0.2.2:8008/api"
       val (request, response, result ) = "/Politicians".httpGet().responseString()
            //responseString { ->
            //make a GET to https://httpbin.org/get and do something with response
        val (data, error) = result
        if (error == null) {
            //do something when success
            politicians = politicianReader(StringReader(data))
            success = true;
            Log.i("Pol:", politicians.toString())
        } else {
            //error handling
            success = false
            Log.i("Json", error.toString())
        }


        return  success
    }
}
