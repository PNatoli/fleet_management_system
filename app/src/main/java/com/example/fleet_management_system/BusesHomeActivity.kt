package com.example.fleet_management_system

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_buses_home.*
import kotlinx.android.synthetic.main.activity_details.*
import java.text.DecimalFormat

class BusesHomeActivity : AppCompatActivity() {

    private val busesArray = ArrayList<BusModel>()
    private val busesMap = mutableMapOf<String, BusModel>()
    private val dbManager = DBManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buses_home)
        // setup nav bar
        setupActionBar()
        //start loader
        loader.visibility = View.VISIBLE
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
            "year" to 1970,
            "odometer" to 100001,
            "wheels" to 6,
            "maxCapacity" to 24,
            "airCond" to true,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus2 = BusModel(mutableMapOf(
            "id" to "2",
            "vin" to "219876231687XYZ",
            "make" to "Ford",
            "model" to "Giant Boy",
            "year" to 1980,
            "odometer" to 100001,
            "wheels" to 6,
            "maxCapacity" to 24,
            "airCond" to true,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus3 = BusModel(mutableMapOf(
            "id" to "3",
            "vin" to "219876231687ABC",
            "make" to "Ford",
            "model" to "Raptor",
            "year" to 1980,
            "odometer" to 100001,
            "wheels" to 6,
            "maxCapacity" to 24,
            "airCond" to false,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus4 = BusModel(mutableMapOf(
            "id" to "4",
            "vin" to "219876231687XYZ",
            "make" to "Ford",
            "model" to "Flyer",
            "year" to 1970,
            "odometer" to 90000,
            "wheels" to 6,
            "maxCapacity" to 24,
            "airCond" to true,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus5 = BusModel(mutableMapOf(
            "id" to "5",
            "vin" to "219876231687ABC",
            "make" to "Chevy",
            "model" to "Ton",
            "year" to 1970,
            "odometer" to 90000,
            "wheels" to 6,
            "maxCapacity" to 24,
            "airCond" to false,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus6 = BusModel(mutableMapOf(
            "id" to "6",
            "vin" to "219876231687XYZ",
            "make" to "Chevy",
            "model" to "Rock",
            "year" to 1980,
            "odometer" to 90000,
            "wheels" to 6,
            "maxCapacity" to 24,
            "airCond" to false,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus7 = BusModel(mutableMapOf(
            "id" to "7",
            "vin" to "219876231687ABC",
            "make" to "Chevy",
            "model" to "Toolbox RR",
            "year" to 1970,
            "odometer" to 100001,
            "wheels" to 6,
            "maxCapacity" to 36,
            "airCond" to true,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus8 = BusModel(mutableMapOf(
            "id" to "8",
            "vin" to "219876231687XYZ",
            "make" to "Dodge",
            "model" to "Big Bus",
            "year" to 1980,
            "odometer" to 100001,
            "wheels" to 6,
            "maxCapacity" to 36,
            "airCond" to true,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus9 = BusModel(mutableMapOf(
            "id" to "9",
            "vin" to "219876231687ABC",
            "make" to "Dodge",
            "model" to "Yellow",
            "year" to 1980,
            "odometer" to 100001,
            "wheels" to 6,
            "maxCapacity" to 36,
            "airCond" to false,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus10 = BusModel(mutableMapOf(
            "id" to "10",
            "vin" to "219876231687XYZ",
            "make" to "Dodge ",
            "model" to "Stretch",
            "year" to 1970,
            "odometer" to 90000,
            "wheels" to 6,
            "maxCapacity" to 36,
            "airCond" to true,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus11 = BusModel(mutableMapOf(
            "id" to "11",
            "vin" to "219876231687ABC",
            "make" to "Honda",
            "model" to "Type R",
            "year" to 1970,
            "odometer" to 90000,
            "wheels" to 6,
            "maxCapacity" to 36,
            "airCond" to false,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus12 = BusModel(mutableMapOf(
            "id" to "12",
            "vin" to "219876231687XYZ",
            "make" to "Honda",
            "model" to "Type RR",
            "year" to 1980,
            "odometer" to 90000,
            "wheels" to 6,
            "maxCapacity" to 36,
            "airCond" to false,
            "currentStatus" to CurrentStatusEnum.READY_FOR_USE
        ))

        val bus13 = BusModel(mutableMapOf(
            "id" to "13",
            "vin" to "219876231687ABC",
            "make" to "Big Yellow",
            "model" to "Lightening",
            "year" to 1970,
            "odometer" to 100001,
            "wheels" to 6,
            "maxCapacity" to 24,
            "airCond" to true,
            "currentStatus" to CurrentStatusEnum.UNDERGOING_REPAIRS
        ))

        val bus14 = BusModel(mutableMapOf(
            "id" to "14",
            "vin" to "219876231687XYZ",
            "make" to "Buick",
            "model" to "Old Truck 11",
            "year" to 2001,
            "odometer" to 99000,
            "wheels" to 6,
            "maxCapacity" to 40,
            "airCond" to false,
            "currentStatus" to CurrentStatusEnum.UNDERGOING_REPAIRS
        ))

        // add buses and get notified when done
        val dispatchGroup = DispatchGroup()
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus1){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus2){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus3){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus4){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus5){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus6){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus7){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus8){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus9){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus10){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus11){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus12){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus13){
            dispatchGroup.leave()
        }
        dispatchGroup.enter()
        dbManager.insertUpdateBusInDB(bus14){
            dispatchGroup.leave()
        }

        dispatchGroup.notify(Runnable{
            // mock data inserts are done, get buses
            getBuses()
        })
    }

    private fun getBuses(){
        dbManager.getBusesFromDB {
            for (entity in it){
                // convert into bus models
                val newBus = BusModel(mutableMapOf(
                    "id" to entity.id,
                    "busImageURL" to entity.busImageURL,
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

            // sort array
            busesArray.sortBy {element ->
                element.id!!.toInt()
            }
            // end loader
            loader.visibility = View.INVISIBLE

            // refresh recycler, update ui thread
            runOnUiThread {
                busesRecyclerView.adapter = BusesRecyclerAdapter(busesArray,this)
            }

            // to satisfy question 2 on the assessment
            // provide a function that returns the resale value of a bus based on ID
            val value = getResaleValue("11")
            println(value)
        }
    }

    // not necessary for my implementation. This function is here to satisfy Question 2 on the assessment.
    private fun getResaleValue(busId: String): String{
        if (!busesMap.containsKey(busId)){
            return "Couldn't find bus id"
        }
        val bus = busesMap[busId]!!

        if (bus.maxCapacity == null || bus.odometer == null || bus.currentStatus != CurrentStatusEnum.READY_FOR_USE || bus.year == null){
            return "Does not qualify"
        }

        // constant for starting prices
        val startingPriceFor24Pass = 120000.0
        val startingPriceFor36Pass = 160000.0

        var price: Double
        when (bus.maxCapacity) {
            // add appropriate amount to starting prices
            24 -> {
                price = startingPriceFor24Pass
                if (bus.airCond){
                    // +3600
                    price += 3600.0
                }
                if (bus.year!! <= 1972){
                    // +40800
                    price += 40800.0
                }
            }

            36 -> {
                price = startingPriceFor36Pass
                if (bus.airCond){
                    // +4800
                    price += 4800.0
                }
                if (bus.year!! <= 1972){
                    // +54400
                    price += 54400.0
                }
            }
            else -> {
                return "Does not qualify"
            }
        }

        if (bus.odometer!! > 100000){
            price -=  (bus.odometer!! - 100000) * .1
        }

        // format the resale value to dollars
        val formatter = DecimalFormat("$###,###,##0.00")
        return formatter.format(price)
    }
}