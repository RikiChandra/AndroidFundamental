package com.example.githubapp.view.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme")


    fun getTheme(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun setTheme(drakModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = drakModeActive
        }
    }


    companion object {
        @Volatile
        private var instance: SettingPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreference =
            instance ?: synchronized(this) {
                instance ?: SettingPreference(dataStore).apply { instance = this }
            }
    }


}