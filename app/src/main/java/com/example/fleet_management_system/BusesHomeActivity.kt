package com.example.fleet_management_system

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_buses_home.*

class BusesHomeActivity : AppCompatActivity() {

    private val busesArray = ArrayList<BusModel>()
    private val busesMap = mutableMapOf<String, BusModel>()
    val dbManager = DBManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buses_home)
        // setup nav bar
        setupActionBar()
        //start loader

        // setupRecycler
        setupRecyclerView()
        // initialize db manager
        dbManager.initialize(this)
        // create some mock buses
        createBuses()
    }

    fun pushToDetailsActivity(position: Int){
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("bus", busesArray[position])
        startActivity(intent)
    }

    private fun setupActionBar() {
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.background)))
    }

    private fun setupRecyclerView(){
        busesRecyclerView.layoutManager = LinearLayoutManager(this)
        busesRecyclerView.isScrollContainer = true
        busesRecyclerView.adapter = BusesRecyclerAdapter(busesArray,this)
    }

    private fun createBuses(){
        // mock data
        val bus1 = BusModel(mutableMapOf(
            "id" to "1",
            "vin" to "219876231687ABC",
            "make" to "Ford",
            "model" to "Big Boy",
            "year" to 2001,
            "odometer" to 99000,
            "wheels" to 6,
            "maxCapacity" to 24,
            "airCond" to false,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus2 = BusModel(mutableMapOf(
            "id" to "2",
            "vin" to "219876231687XYZ",
            "make" to "Ford",
            "model" to "Giant Boy",
            "year" to 2001,
            "odometer" to 99000,
            "wheels" to 6,
            "maxCapacity" to 24,
            "airCond" to false,
            "currentStatus" to CurrentStatusEnum.UNDERGOING_REPAIRS
        ))

        val dispatchGroup = DispatchGroup()
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus1){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus2){
            dispatchGroup.leave()
        }

        dispatchGroup.notify(Runnable{
            // mock data inserts are done
            getBuses()
        })
    }

    private fun getBuses(){
        dbManager.getBusesFromDB {
            for (entity in it){
                // convert into bus models
                val newBus = BusModel(mutableMapOf(
                    "id" to entity.id,
                    "vin" to entity.vin,
                    "make" to entity.make,
                    "model" to entity.model,
                    "year" to entity.year,
                    "odometer" to entity.odometer,
                    "wheels" to entity.wheels,
                    "maxCapacity" to entity.maxCapacity,
                    "airCond" to entity.airCond,
                    "currentStatus" to entity.currentStatus
                ) as MutableMap<String, Any>)

                // adding the newBus to both the array and map
                // we are going to build the UI with the array
                // busesMap is to satisfy Question 2 in the assessment
                busesArray.add(newBus)
                busesMap[newBus.id!!] = newBus
            }
            // end loader
            // refresh recycler
            busesRecyclerView.adapter = BusesRecyclerAdapter(busesArray,this)
        }
    }

    // not necessary for my implementation. This function is here to satisfy Question 2 on the assessment.
    private fun getResaleValue(busId: String): Double{
        if (!busesMap.containsKey(busId)){
            return 0.0
        }
        val bus = busesMap[busId]!!

        if (bus.maxCapacity == null || bus.odometer == null || bus.currentStatus != CurrentStatusEnum.READY_FOR_USE || bus.year == null){
            return 0.0
        }

        var price: Double = 0.0
        price = when (bus.maxCapacity) {
            24 -> {
                120000.0
            }
            36 -> {
                160000.0
            }
            else -> {
                return 0.0
            }
        }

        if (bus.airCond){
            price += price * .03
        }

        if (bus.year!! <= 1972){
            price += price * .34
        }

        if (bus.odometer!! > 100000){
            price -=  (bus.odometer!! - 100000) * .1
        }

        // set resale value
        return price
    }
}