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
import java.lang.reflect.Array


class PoliticianController {
    var politicians = ArrayList<Politician>()
    var issues = ArrayList<Issue>()
    var bio:Bio = Bio(-1,"","","","", "")

    fun getPoli(): ArrayList<Politician>{
        return politicians
    }
    fun getIss(): ArrayList<Issue>{
        return issues
    }
    fun getBi(): Bio{
        return bio
    }
    fun politicianReader(strReader: StringReader) : ArrayList<Politician>{


        val result = ArrayList<Politician>()

        val reader = JsonReader( strReader)

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


    fun issueReader(strReader: StringReader) : ArrayList<Issue>{


        val result = ArrayList<Issue>()

        val reader = JsonReader(strReader)

        reader.beginArray()
        while (reader.hasNext()) {
            var id: Int = -1
            var topic: String = ""
            var issueDescription: String = ""
            var person: Int = -1
            var answer: String = ""

            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "id" -> id = reader.nextInt()
                    "topic" -> topic = reader.nextString()
                    "description" -> issueDescription = reader.nextString()
                    "person" -> person = reader.nextInt()
                    "answerText" -> answer = reader.nextString()


                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            val issue = Issue(id, person,  topic, issueDescription, answer)
            result.add(issue)
        }
        reader.endArray()
        return result
    }

    fun bioReader(strReader: StringReader) : Bio{


        val result = ArrayList<Issue>()

        val reader = JsonReader(strReader)

        reader.beginArray()

        var id: Int = -1
        var gender: String = ""
        var photo: String = ""
        var family: String = ""
        var religion: String = ""
        var specialMessage: String = ""

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "person" -> id = reader.nextInt()
                "gender" -> gender = reader.nextString()
                "photo" -> photo = reader.nextString()
                "family" -> family = reader.nextString()
                "religion" -> religion = reader.nextString()
                "special_msg" -> specialMessage = reader.nextString()


                else -> reader.skipValue()
            }
        }
        reader.endObject()

        val blah = Bio(id, gender, photo, family, religion, specialMessage)
        Log.i("blah,", blah.photo)
        return blah

    }

    fun callIssues(identifier: Int) : Boolean{
        var success: Boolean

        var fullString : String = identifier.toString() + "/Issue"

        FuelManager.instance.basePath = "http:/10.0.2.2:8008/api"


        val (request, response, result ) = fullString.httpGet().responseString()
        //responseString { ->
        //make a GET to https://httpbin.org/get and do something with response
        val (data, error) = result
        if (error == null) {
            //do something when success
            issues = issueReader(StringReader(data))
            success = true;
            Log.i("Pol:", politicians.toString())
        } else {
            //error handling
            success = false
            Log.i("Json", error.toString())
        }


        return  success
    }


    fun callPoliticians() : Boolean{
        var success : Boolean


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

    fun callBio(identifier: Int) : Boolean {
        var success: Boolean

        var fullString : String = identifier.toString() + "/Bio"
        FuelManager.instance.basePath = "http:/10.0.2.2:8008/api"
        val (request, response, result ) = fullString.httpGet().responseString()
        //responseString { ->
        //make a GET to https://httpbin.org/get and do something with response
        val (data, error) = result
        if (error == null) {
            //do something when success
            bio = bioReader(StringReader(data))
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
