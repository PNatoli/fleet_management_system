package com.example.fleet_management_system

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bus_Entity (
    @PrimaryKey var id: String,
    @ColumnInfo(name = "vin") var vin: String? = "N/A",
    @ColumnInfo(name = "make") var make: String? = "",
    @ColumnInfo(name = "model") var model:  String? = "",
    @ColumnInfo(name = "year") var year: Int? = null,
    @ColumnInfo(name = "odometer") var odometer: Int? = null,
    @ColumnInfo(name = "wheels") var wheels:  Int? = null,
    @ColumnInfo(name = "maxCapacity") var maxCapacity: Int? = null,
    @ColumnInfo(name = "airCond") var airCond: Boolean = false,
    @ColumnInfo(name = "currentStatus") var currentStatus:  CurrentStatusEnum? = CurrentStatusEnum.READY_FOR_USE
    )