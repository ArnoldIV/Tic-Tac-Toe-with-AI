package com.arnold.tic_tac_toewithai

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.arnold.tic_tac_toewithai.util.Constants.Companion.DEFAULT_AMOUNT_OF_MONEY
import com.arnold.tic_tac_toewithai.util.Constants.Companion.PREFERENCES_AMOUNT_OF_MONEY
import com.arnold.tic_tac_toewithai.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {
        val amountOfMoney = intPreferencesKey(PREFERENCES_AMOUNT_OF_MONEY)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveAmountOfMoney(money: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.amountOfMoney] = money
        }
    }

    val readAmountOfMoney: Flow<AmountOfMoney> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val money = preferences[PreferencesKeys.amountOfMoney]
            AmountOfMoney(
                money as Int
            )
        }
}

data class AmountOfMoney(
    val money: Int
)

