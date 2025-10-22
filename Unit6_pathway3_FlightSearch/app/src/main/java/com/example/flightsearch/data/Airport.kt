package com.example.flightsearch.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "iata_code") val iataCode: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "passengers") val passengers: Int
)
