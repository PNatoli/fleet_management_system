package com.example.fleet_management_system

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class BusesRecyclerAdapter(var buses: ArrayList<BusModel>, var activity: BusesHomeActivity): RecyclerView.Adapter<BusesRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.busID.text = "Bus ID"
        holder.busCard.setOnClickListener {
            activity.pushToDetailsActivity(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_bus_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return buses.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val busID = itemView.findViewById<TextView>(R.id.busID)!!
        val busCard = itemView.findViewById<View>(R.id.busCard)
    }
}