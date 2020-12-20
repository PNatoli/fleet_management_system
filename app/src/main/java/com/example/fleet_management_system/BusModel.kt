package com.example.fleet_management_system

import androidx.room.TypeConverter
import java.io.Serializable
import java.text.DecimalFormat

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
    var busImageUrl: String? = ""
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
        this.busImageUrl = busMap["busImageURL"] as? String
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

    fun getResaleValue() {

        if (currentStatus == CurrentStatusEnum.READY_FOR_USE && (maxCapacity == 24 || maxCapacity == 36)) {
            var price: Double

            // constant for starting prices
            val startingPriceFor24Pass = 120000.0
            val startingPriceFor36Pass = 160000.0

            when (maxCapacity) {
                // add appropriate amount to starting prices
                24 -> {
                    price = startingPriceFor24Pass
                    if (airCond) {
                        // +3600
                        price += 3600.0
                    }
                    if (year!! <= 1972) {
                        // +40800
                        price += 40800.0
                    }
                }

                36 -> {
                    price = startingPriceFor36Pass
                    if (airCond) {
                        // +4800
                        price += 4800.0
                    }
                    if (year!! <= 1972) {
                        // +54400
                        price += 54400.0
                    }
                }
                else -> {
                    return
                }
            }

            if (odometer!! > 100000) {
                price -= (odometer!! - 100000) * .1
            }

            // set resale value
            this.resaleValue = price
        } else {
            // set to 0.0
            this.resaleValue = 0.0
        }
    }
}