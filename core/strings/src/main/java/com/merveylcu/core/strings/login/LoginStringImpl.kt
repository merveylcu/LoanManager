package com.merveylcu.core.strings.login

import android.content.Context
import com.merveylcu.core.strings.R

class LoginStringsImpl(private val context: Context) : LoginStrings {

    override val loginPageTitle: String
        get() = context.getString(R.string.login_page_title)

    override val loginEmail: String
        get() = context.getString(R.string.login_email)

    override val loginPassword: String
        get() = context.getString(R.string.login_password)

    override val loginButton: String
        get() = context.getString(R.string.login_button)
}
