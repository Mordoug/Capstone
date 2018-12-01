package com.example.morganseielstad.politicado

import java.io.Serializable

data class Politician(val id:Int, val firstName:String, val lastName:String, val state:String, val officeName:String) : Serializable

data class Issue(val id:Int, val issueName:String, val issueDescription:String)
