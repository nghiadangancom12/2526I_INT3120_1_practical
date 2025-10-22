package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect

class FlightRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao
) {
    fun autocomplete(prefix: String): Flow<List<Airport>> =
        if (prefix.isBlank()) emptyFlowList() else airportDao.autocomplete(prefix)

    fun destinationsForDeparture(dep: String): Flow<List<Airport>> =
        if (dep.isBlank()) emptyFlowList() else airportDao.flights(dep)

    fun getAirportByCode(code: String): Flow<Airport?> = airportDao.getByCode(code)

    fun favorites(): Flow<List<Favorite>> = favoriteDao.favorites()

    fun favoriteOf(dep: String, dst: String): Flow<Favorite?> = favoriteDao.find(dep, dst)

    suspend fun addOrRemoveFavorite(dep: String, dst: String) {
        val existing = favoriteDao.find(dep, dst).firstOrNull()
        if (existing == null) {
            favoriteDao.insert(Favorite(departure_code = dep, destination_code = dst))
        } else {
            favoriteDao.delete(existing)
        }
    }
}

private fun <T> emptyFlowList(): Flow<List<T>> = flow { emit(emptyList()) }

private suspend fun <T> Flow<T>.firstOrNull(): T? {
    var v: T? = null
    collect { if (v == null) v = it }
    return v
}
