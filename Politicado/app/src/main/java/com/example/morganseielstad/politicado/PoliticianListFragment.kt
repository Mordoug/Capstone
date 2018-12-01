package com.example.morganseielstad.politicado

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.R.attr.layoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread
import java.sql.Time

class PoliticianListFragment : Fragment() {
    private lateinit var currentLayoutManagerType: LayoutManagerType
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    val pController: PoliticianController = PoliticianController()
    var myDataset = ArrayList<Politician>()

    enum class LayoutManagerType { GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.politician_list)
        initData()

        Log.i("recycle", "onCreate")
        //myDataset = intent.getSerializableExtra("politician") as ArrayList<Politician>


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        Log.i("Recycle", "onCreateView")

        val rootView = inflater.inflate( R.layout.politician_list, container, false).apply { tag = "TAG" }

        recyclerView = rootView.findViewById(R.id.recycle_poly)
        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER
        layoutManager = LinearLayoutManager(activity)

        //recyclerView.layoutManager = LinearLayoutManager(activity)

        layoutManager = this@PoliticianListFragment.layoutManager
        recyclerView.adapter = MyAdapter(myDataset)

        //scrollToPosition(0)

        //layoutManager = LinearLayoutManager(activity)

        return rootView

    }

    private fun initData() {
        doAsyncResult {
            pController.callPoliticians()
        }.get()

        myDataset = pController.getPoli()



        //myDataset.add(Politician(firstName = "Morgan", lastName = "S", id = 1123, state = "VT", officeName = "President"))
        //myDataset.add(Politician(firstName = "Morgan", lastName = "Se", id = 1123, state = "VT", officeName = "President"))
        //myDataset.add(Politician(firstName = "Morgan", lastName = "Sei", id = 1123, state = "VT", officeName = "President"))
        //myDataset.add(Politician(firstName = "Morgan", lastName = "Seie", id = 1123, state = "VT", officeName = "President"))
    }

}