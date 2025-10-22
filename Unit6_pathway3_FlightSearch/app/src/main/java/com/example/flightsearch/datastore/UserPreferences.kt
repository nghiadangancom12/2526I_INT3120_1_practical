package com.example.flightsearch.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DS_NAME = "user_prefs"
val Context.dataStore by preferencesDataStore(DS_NAME)

class UserPreferences(private val context: Context) {
    private val KEY_SEARCH = stringPreferencesKey("search_query")

    val searchQuery: Flow<String> = context.dataStore.data
        .map { it[KEY_SEARCH] ?: "" }

    suspend fun setSearchQuery(q: String) {
        context.dataStore.edit { it[KEY_SEARCH] = q }
    }
}
