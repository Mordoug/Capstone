package com.example.morganseielstad.politicado

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import org.jetbrains.anko.doAsyncResult

import com.squareup.picasso.Picasso
import java.net.URI
import java.net.URL


class PoliticianFragment (private val politician: Politician) : Fragment() {
    private lateinit var currentLayoutManagerType: PoliticianFragment.LayoutManagerType
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private val pController : PoliticianController = PoliticianController()
    private var issues =  ArrayList<Issue>()
    private var bio: Bio = Bio(0,"","","","","")

    enum class LayoutManagerType { GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        Log.i("init", "Complete")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(
            R.layout.politician_view,
            container, false
        ).apply { tag = "Tag" }

        val name : TextView = rootView.findViewById(R.id.politician_name_text)
        val office : TextView = rootView.findViewById(R.id.politician_office)
        //val state : TextView = rootView.findViewById(R.id.politician_state)
        val imageView: ImageView = rootView.findViewById(R.id.headshot)

        val path: String? = bio.photo


        name.text = politician.firstName + " " + politician.lastName
        office.text = politician.state + " " + politician.officeName


        if (path != ""){
            Picasso.get().load(path).into(imageView)
        }

        recyclerView = rootView.findViewById(R.id.stance)
        currentLayoutManagerType = PoliticianFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = this@PoliticianFragment.layoutManager
        recyclerView.adapter = IssueRecyclerAdapter(issues)

        return rootView

    }

    private fun initData() {
        doAsyncResult {
            pController.callIssues(politician.id)
        }.get()
        doAsyncResult {
            pController.callBio(politician.id)
        }.get()
        issues = pController.getIss()
        bio = pController.getBi()
        Log.i("Tag", bio.photo)
        if (issues.size == 0) {
            issues.add(Issue(-1, -1, "No Issues", "No Responses to the Votesmart Surveys have been recorded", ""))
        }
    }


}



