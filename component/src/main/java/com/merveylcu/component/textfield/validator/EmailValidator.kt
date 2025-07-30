package com.merveylcu.component.textfield.validator

class EmailValidator(
    override val errorMessage: String = "Error"
) : LoanTextFieldStringValidator {

    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    override fun validate(candidate: String?): Boolean {
        return !candidate.isNullOrBlank() && emailRegex.matches(candidate)
    }
}
