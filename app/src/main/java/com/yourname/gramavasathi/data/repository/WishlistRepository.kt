package com.yourname.gramavasathi.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishlistRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val WISHLIST_KEY = stringSetPreferencesKey("wishlist_ids")

    fun getWishlistIds(): Flow<Set<String>> = dataStore.data
        .catch { emit(androidx.datastore.preferences.core.emptyPreferences()) }
        .map { prefs -> prefs[WISHLIST_KEY] ?: emptySet() }

    suspend fun toggleWishlist(listingId: String) {
        dataStore.edit { prefs ->
            val current = prefs[WISHLIST_KEY]?.toMutableSet() ?: mutableSetOf()
            if (listingId in current) current.remove(listingId)
            else current.add(listingId)
            prefs[WISHLIST_KEY] = current
        }
    }

    fun isWishlisted(listingId: String): Flow<Boolean> =
        getWishlistIds().map { it.contains(listingId) }
}