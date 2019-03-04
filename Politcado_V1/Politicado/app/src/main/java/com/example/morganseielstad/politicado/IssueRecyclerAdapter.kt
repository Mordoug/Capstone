package com.example.morganseielstad.politicado


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class IssueRecyclerAdapter(private val myDataset: ArrayList<Issue>) : RecyclerView.Adapter<IssueRecyclerAdapter.IViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class IViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val topic: TextView
        val description: TextView
        val answer: TextView
        init {
            // Define click listener for the ViewHolder's View.
            //v.setOnClickListener { Log.d(TAG, "Element $adapterPosition clicked.") }
            topic = v.findViewById(R.id.topic)
            description = v.findViewById(R.id.description)
            answer = v.findViewById(R.id.answer)
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueRecyclerAdapter.IViewHolder {
        // create a new view
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.issue_list_row, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return IViewHolder(cell)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.topic.text = myDataset[position].topic // + " " + myDataset[position].lastName
        holder.description.text = myDataset[position].issueDescription
        holder.answer.text = myDataset[position].answer


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() : Int {
        return myDataset.size
    }
}