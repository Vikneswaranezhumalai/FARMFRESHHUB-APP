package com.txstate.sampleapicall.utils

import android.R
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.txstate.sampleapicall.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Extension property to create DataStore
val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferences {

    private val KEY_USER_ID = intPreferencesKey("user_id")
    private val KEY_EMAIL = stringPreferencesKey("user_email")
    private val KEY_ROLE = stringPreferencesKey("user_role")
    private val KEY_NAME = stringPreferencesKey("user_name")
    private val KEY_LOGIN_STATUS = booleanPreferencesKey("login_status")

    // Save user info
    suspend fun saveUser(context: Context, user : User) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_ID] = user.userId
            prefs[KEY_EMAIL] = user.email
            prefs[KEY_ROLE] = user.userType
            prefs[KEY_NAME] = user.name
            prefs[KEY_LOGIN_STATUS] = true
        }
    }

    // Read userId
    fun getUserId(context: Context): Flow<Int?> {
        return context.dataStore.data.map { prefs ->
            prefs[KEY_USER_ID]
        }
    }

    // Read userRole
    fun getUserRole(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[KEY_ROLE]
        }
    }
    fun getUserName(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[KEY_NAME]
        }
    }

   suspend fun updateLoginStatus(context: Context , loginStatus: Boolean) : Boolean {
        context.dataStore.edit { prefs ->
            prefs[KEY_LOGIN_STATUS] = loginStatus
        }
        return true
    }

    // Optional: Clear user session
    suspend fun clearUser(context: Context) {
        context.dataStore.edit { it.clear() }
    }
}

