package com.example.morganseielstad.politicado

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.OrientationEventListener

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.politician_list_row.view.*

class IssueRecyclerAdapter(private val myDataset: ArrayList<Issue>, val listener: (Int) -> Unit) : RecyclerView.Adapter<IssueRecyclerAdapter.IViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class IViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView
        val office: TextView
        init {
            // Define click listener for the ViewHolder's View.
            //v.setOnClickListener { Log.d(TAG, "Element $adapterPosition clicked.") }
            Log.i("ViewHolder", "Init")
            name = v.findViewById(R.id.name)
            office = v.findViewById(R.id.office)
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueRecyclerAdapter.IViewHolder {
        // create a new view
        Log.i("onCreateViewHolder", "here")
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.politician_list_row, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return IViewHolder(cell)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.i("RecycleAdapter", "onBindViewHolder")
        holder.name.text = myDataset[position].topic // + " " + myDataset[position].lastName
        holder.office.text = myDataset[position].issueDescription

        holder.itemView.setOnClickListener {
            listener(myDataset[position].id)

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() : Int {
        Log.i("Item Count Issue", myDataset.size.toString())
        return myDataset.size
    }
}