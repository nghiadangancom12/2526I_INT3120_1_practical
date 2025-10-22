package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("""
        SELECT * FROM airport 
        WHERE iata_code LIKE :prefix || '%' 
           OR name LIKE '%' || :prefix || '%' 
        ORDER BY passengers DESC 
        LIMIT 10
    """)
    fun autocomplete(prefix: String): Flow<List<Airport>>

    @Query("""
        SELECT a2.* FROM airport a2 
        WHERE a2.iata_code <> :departure 
        ORDER BY a2.passengers DESC
    """)
    fun flights(departure: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code = :code LIMIT 1")
    fun getByCode(code: String): Flow<Airport?>
}
