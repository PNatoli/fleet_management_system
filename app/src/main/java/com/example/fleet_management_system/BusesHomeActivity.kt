package com.example.fleet_management_system

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_buses_home.*
import java.text.DecimalFormat

class BusesHomeActivity : AppCompatActivity() {
    private val busesArray = ArrayList<BusModel>()
    private val busesMap = mutableMapOf<String, BusModel>()
    private val dbManager = DBManager()
    private val userPref = UserPref()
    private val firstEverLaunch = "true"
    var firstOpen = false

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

        // create buses first time
        checkIfFirstTimeRunningApp()
    }

    override fun onResume() {
        super.onResume()
        // only call if not first time running app (returning to screen to get updated info)
        if (firstOpen){
            //start loader
            loader.visibility = View.VISIBLE
            getBuses()
        }
        firstOpen = true
    }

    private fun checkIfFirstTimeRunningApp(){
        // create some mock buses if first time running app
        // keep track of userPref
        if (userPref.getPrefs(firstEverLaunch, this) == 0){
            userPref.setPrefs(firstEverLaunch, this)
            createBuses()
            println("first time running app")
        } else {
            // we have buses already, just get them
            getBuses()
        }
        // kill instance
        userPref.finishAffinity()
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
        window.navigationBarColor = resources.getColor(R.color.background)
    }

    private fun setupRecyclerView(){
        busesRecyclerView.layoutManager = LinearLayoutManager(this)
        busesRecyclerView.isScrollContainer = true
        busesRecyclerView.adapter = BusesRecyclerAdapter(busesArray,this)
    }

    // only runs on first time we launch app to populate mock data
    private fun createBuses(){
        // only run on first time running app
        // to get original data set back, uninstall and reload app
        // mock data
        val bus1 = BusModel(mutableMapOf(
            "id" to "1",
            "vin" to "219876231687ABC",
            "busImageURL" to "https://www.continentalbuslines.com/wp-content/uploads/2016/10/Group-Charters-Section-6.jpg",
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
            "busImageURL" to "https://www.brokenurl123.com",
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
            "busImageURL" to "https://i1.wp.com/wheelchairtravel.org/wp-content/uploads/2017/08/hop-on-hop-off-bus-feature-facebook-2.jpg?fit=1200%2C630&ssl=1",
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
            "busImageURL" to "https://www.rusalia.com/wp-content/uploads/2018/05/Viajar-en-autobus-en-Rusia-Imagen-destacada.jpg",
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
            "busImageURL" to "",
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
            "busImageURL" to "https://m.dw.com/image/16987108_401.jpg",
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
            "busImageUrl" to "https://bioage.typepad.com/.a/6a00d8341c4fbe53ef0240a4eaaccc200b-550wi",
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
            "busImageURL" to "https://thedriven.io/wp-content/uploads/2020/04/screenshot-volgren.com_.au-2020.04.27-14_47_02-1280x720.jpg",
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
            "busImageURL" to "",
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
            "busImageURL" to "https://cdn.cnn.com/cnnnext/dam/assets/200826183306-adventures-overlandimage-from-ios.jpg",
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
            "busImageURL" to "https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/LT_471_%28LTZ_1471%29_Arriva_London_New_Routemaster_%2819522859218%29.jpg/1200px-LT_471_%28LTZ_1471%29_Arriva_London_New_Routemaster_%2819522859218%29.jpg",
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
            "busImageURL" to "https://etimg.etb2bimg.com/photo/72057731.cms",
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
            "busImageURL" to "https://www.ohio.edu/sites/ohio.edu.news/files/2020-01/go_bus_bws-1200px.jpg",
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
            "busImageURL" to "https://reneweconomy.com.au/wp-content/uploads/2017/08/ACT-electric-bus.jpg",
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
        // clear array & map
        busesArray.clear()
        busesMap.clear()

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

    // **Not necessary for my implementation. This function is here to satisfy Question 2 on the assessment.
    private fun getResaleValue(busId: String): String{
        if (!busesMap.containsKey(busId)){
            return "Couldn't find bus id"
        }
        val bus = busesMap[busId]!!
        var price = 0.0

        if (bus.currentStatus == CurrentStatusEnum.READY_FOR_USE && (bus.maxCapacity == 24 || bus.maxCapacity == 36)) {

            // constant for starting prices
            val startingPriceFor24Pass = 120000.0
            val startingPriceFor36Pass = 160000.0

            when (bus.maxCapacity) {
                // add appropriate amount to starting prices
                24 -> {
                    price = startingPriceFor24Pass
                    if (bus.airCond) {
                        // +3600
                        price += 3600.0
                    }
                    if (bus.year!! <= 1972) {
                        // +40800
                        price += 40800.0
                    }
                }

                36 -> {
                    price = startingPriceFor36Pass
                    if (bus.airCond) {
                        // +4800
                        price += 4800.0
                    }
                    if (bus.year!! <= 1972) {
                        // +54400
                        price += 54400.0
                    }
                }
                else -> {
                    return "Max Capacity error"
                }
            }

            if (bus.odometer!! > 100000) {
                price -= (bus.odometer!! - 100000) * .1
            }

            // set resale value
            bus.resaleValue = price
        } else {
            // set to 0.0
            bus.resaleValue = 0.0
        }

        // format the resale value to dollars
        val formatter = DecimalFormat("$###,###,##0.00")
        return "Bus ID $busId resale value: " + formatter.format(price)
    }
}