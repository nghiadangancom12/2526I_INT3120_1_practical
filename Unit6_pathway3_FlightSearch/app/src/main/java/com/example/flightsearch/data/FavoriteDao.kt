package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert
    suspend fun insert(fav: Favorite)

    @Delete
    suspend fun delete(fav: Favorite)

    @Query("""
        SELECT * FROM favorite 
        WHERE departure_code = :dep AND destination_code = :dst 
        LIMIT 1
    """)
    fun find(dep: String, dst: String): Flow<Favorite?>

    @Query("SELECT * FROM favorite ORDER BY id DESC")
    fun favorites(): Flow<List<Favorite>>
}
