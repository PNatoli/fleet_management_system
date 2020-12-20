package com.example.fleet_management_system

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.ArrayList

class BusesRecyclerAdapter(var buses: ArrayList<BusModel>, var activity: BusesHomeActivity): RecyclerView.Adapter<BusesRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.busCard.clipToOutline = true
        holder.busImageView.clipToOutline = true
        // TODO: sloppy, will not work with longer names.. fix if time
        holder.busDescription.text = "Bus ID: ${buses[position].id}\nMake:   ${buses[position].make}\nModel:  ${buses[position].model}\nYear:    ${buses[position].year}"
        holder.busCard.setOnClickListener {
            activity.pushToDetailsActivity(position)
        }
        // broken or missing url will use default image
        // use glide, add bus images to cards
        Glide.with(holder.itemView.context)
            .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.no_photo_avail))
            .load(buses[position].busImageUrl)
            .into(holder.busImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_bus_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return buses.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val busImageView = itemView.findViewById<ImageView>(R.id.busImageView)!!
        val busDescription = itemView.findViewById<TextView>(R.id.busDescription)!!
        val busCard = itemView.findViewById<View>(R.id.busCard)!!
    }
}