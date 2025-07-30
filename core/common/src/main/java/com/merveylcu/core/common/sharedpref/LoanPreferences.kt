package com.merveylcu.core.common.sharedpref

interface LoanPreferences {

    fun putString(key: LoanPreferencesKey, value: String)

    fun getString(key: LoanPreferencesKey): String?

    fun remove(key: LoanPreferencesKey)

    fun clear()
}
