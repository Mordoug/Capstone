package com.example.morganseielstad.politicado

import java.io.Serializable


data class Politician(val id:Int, val firstName:String, val lastName:String, val state:String, val officeName:String) : Serializable

data class Bio(val id:Int, val gender:String, val photo:String, val family:String, val religion:String, val specialMessage:String) : Serializable

data class Issue(val id:Int, val person:Int,  val topic:String, val issueDescription:String, val answer:String) : Serializable

