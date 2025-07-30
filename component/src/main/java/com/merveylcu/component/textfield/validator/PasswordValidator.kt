package com.merveylcu.component.textfield.validator

class PasswordValidator(
    private val length: Int,
    override val errorMessage: String = "Error"
) : LoanTextFieldStringValidator {

    private val onlyDigitsRegex = Regex("^\\d+$")

    override fun validate(candidate: String?): Boolean {
        return !candidate.isNullOrBlank() &&
                candidate.length == length &&
                onlyDigitsRegex.matches(candidate)
    }
}
