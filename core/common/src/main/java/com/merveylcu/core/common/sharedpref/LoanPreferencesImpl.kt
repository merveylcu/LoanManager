package com.merveylcu.core.common.sharedpref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val LOAN_PREFERENCES_NAME = "LoanPreferences"

@Singleton
internal class LoanPreferencesImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : LoanPreferences {

    private val sharedPreferences: SharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            LOAN_PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun putString(key: LoanPreferencesKey, value: String) {
        sharedPreferences.edit { putString(key.name, value) }
    }

    override fun getString(key: LoanPreferencesKey): String? {
        return sharedPreferences.getString(key.name, null)
    }

    override fun remove(key: LoanPreferencesKey) {
        sharedPreferences.edit { remove(key.name) }
    }

    override fun clear() {
        sharedPreferences.edit { clear() }
    }
}
