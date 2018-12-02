package com.example.morganseielstad.politicado

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.intellij.lang.annotations.Identifier
import org.jetbrains.anko.doAsyncResult

class PoliticianFragment (private val identifier: Int) : Fragment() {
    private lateinit var currentLayoutManagerType: PoliticianFragment.LayoutManagerType
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private val pController : PoliticianController = PoliticianController()
    private var issues =  ArrayList<Issue>()


    enum class LayoutManagerType { GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(
            R.layout.politician_view,
            container, false
        ).apply { tag = "Tag" }

        recyclerView = rootView.findViewById(R.id.stance)
        currentLayoutManagerType = PoliticianFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = this@PoliticianFragment.layoutManager
        Log.i("isEmpty", issues.size.toString())
        recyclerView.adapter = IssueRecyclerAdapter(issues) { id ->
        Log.i("ft:", id.toString())

        }
        return rootView
    }

    private fun initData() {
        doAsyncResult {
            pController.callIssues(identifier)
        }.get()

        issues = pController.getIss()
        if (issues.size == 0) {
            issues.add(Issue(-1, -1, "NO Issues", "No responses", ""))
        }
    }
}