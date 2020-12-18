package com.example.fleet_management_system

import androidx.room.*

@Dao
interface Bus_Entity_Dao{

    @Query("SELECT * FROM Bus_Entity")
    fun getBuses(): List<Bus_Entity>

    @Query("SELECT * FROM Bus_Entity WHERE id = :bus_id")
    fun getBus(bus_id: String): Bus_Entity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpdateBus(vararg users: Bus_Entity)

    @Update
    fun updateBus(vararg bus: Bus_Entity)
}