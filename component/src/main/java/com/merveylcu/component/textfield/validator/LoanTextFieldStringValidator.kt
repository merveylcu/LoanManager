package com.merveylcu.component.textfield.validator

interface LoanTextFieldValidator<T> {
    val errorMessage: String
    fun validate(candidate: T?): Boolean
}

interface LoanTextFieldStringValidator : LoanTextFieldValidator<String>
