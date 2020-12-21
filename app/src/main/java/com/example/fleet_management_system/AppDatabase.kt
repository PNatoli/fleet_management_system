package com.example.fleet_management_system

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// configuration for room setup
@Database(entities = [Bus_Entity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun Bus_Entity_Dao(): Bus_Entity_Dao
}