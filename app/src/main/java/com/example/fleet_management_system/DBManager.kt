package com.example.fleet_management_system

import android.content.Context
import androidx.room.Room
import java.util.concurrent.Executors

class DBManager {

    var db: AppDatabase? = null
    var context: Context? = null

    fun initialize(context: Context){
        db = Room.databaseBuilder(context, AppDatabase::class.java, "database-name").build()
        this.context = context
    }

    fun getBusesFromDB(callback: (List<Bus_Entity>) -> Unit){
        val myExecutor = Executors.newSingleThreadExecutor()
        myExecutor.execute {
            val result = db!!.Bus_Entity_Dao().getBuses()
            callback(result)
        }
    }

    fun insertUpdateBusInDB(bus: BusModel, callback: () -> Unit){
        val myExecutor = Executors.newSingleThreadExecutor()
        myExecutor.execute {
            val busEnt = Bus_Entity(bus.id!!)
            busEnt.vin = bus.vin
            busEnt.busImageURL = bus.busImageUrl
            busEnt.make = bus.make
            busEnt.model = bus.model
            busEnt.year = bus.year
            busEnt.odometer = bus.odometer
            busEnt.wheels = bus.wheels
            busEnt.maxCapacity = bus.maxCapacity
            busEnt.airCond = bus.airCond
            busEnt.currentStatus = bus.currentStatus

            db!!.Bus_Entity_Dao().insertUpdateBus(busEnt)

            callback()
        }
    }
}