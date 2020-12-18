package com.example.fleet_management_system

import androidx.room.TypeConverter
import java.io.Serializable

enum class CurrentStatusEnum{READY_FOR_USE, SCHEDULED_FOR_MAINTENANCE, UNDERGOING_REPAIRS}

class Converters {
    @TypeConverter
    fun fromEnum(value: CurrentStatusEnum): Int {
        return when (value){
            CurrentStatusEnum.READY_FOR_USE -> 0
            CurrentStatusEnum.UNDERGOING_REPAIRS -> 1
            CurrentStatusEnum.SCHEDULED_FOR_MAINTENANCE -> 2
        }
    }

    @TypeConverter
    fun toEnum(value: Int): CurrentStatusEnum {
        return when (value){
            0 -> CurrentStatusEnum.READY_FOR_USE
            1 -> CurrentStatusEnum.UNDERGOING_REPAIRS
            2 -> CurrentStatusEnum.SCHEDULED_FOR_MAINTENANCE
            else -> CurrentStatusEnum.READY_FOR_USE
        }
    }
}

data class BusModel(var busMap: MutableMap<String, Any>): Serializable {
    var id: String? = "N/A"
    var vin: String? = "N/A"
    var make: String? = ""
    var model: String? = ""
    var year: Int? = null
    var odometer: Int? = null
    var wheels: Int? = null
    var maxCapacity: Int? = null
    var airCond = false
    var currentStatus: CurrentStatusEnum? = CurrentStatusEnum.READY_FOR_USE
    var resaleValue = 0.0

    init {
        this.id = busMap["id"] as? String
        this.vin = busMap["vin"] as? String
        this.make = busMap["make"] as? String
        this.model = busMap["model"] as? String
        this.year = busMap["year"] as? Int
        this.odometer = busMap["odometer"] as? Int
        this.wheels = busMap["wheels"] as? Int
        this.maxCapacity = busMap["maxCapacity"] as? Int
        (busMap["airCond"] as? Boolean)?.let {
            this.airCond = it
        }
        this.currentStatus = busMap["currentStatus"] as? CurrentStatusEnum
        getResaleValue()
    }

    private fun getResaleValue(){
        if (maxCapacity == null || odometer == null || currentStatus != CurrentStatusEnum.READY_FOR_USE || year == null){
            return
        }

        var price: Double = 0.0
        price = when (maxCapacity) {
            24 -> {
                120000.0
            }
            36 -> {
                160000.0
            }
            else -> {
                return
            }
        }

        if (airCond){
            price += price * .03
        }

        if (year!! <= 1972){
            price += price * .34
        }

        if (odometer!! > 100000){
            price -=  (odometer!! - 100000) * .1
        }

        // set resale value
        this.resaleValue = price
    }
}