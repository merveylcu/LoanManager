package com.merveylcu.feature.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.merveylcu.component.button.LoanButton
import com.merveylcu.component.text.LoanText
import com.merveylcu.component.textfield.LoanTextField
import com.merveylcu.component.textfield.LoanTextFieldValue
import com.merveylcu.component.textfield.validator.EmailValidator
import com.merveylcu.component.textfield.validator.PasswordValidator
import com.merveylcu.core.theme.LocalAppDimensions
import com.merveylcu.core.theme.LocalAppSpacing
import com.merveylcu.core.theme.LocalAppTextSizes

private const val PASSWORD_MAX_LENGTH = 6

@Composable
internal fun LoginRootScreen(
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        uiState = uiState,
        actions = viewModel
    )
}

@Composable
internal fun LoginScreen(
    uiState: LoginUiState,
    actions: LoginActions
) {
    val emailValidators = remember {
        listOf(EmailValidator())
    }
    val passwordValidators = remember(PASSWORD_MAX_LENGTH) {
        listOf(PasswordValidator(length = PASSWORD_MAX_LENGTH))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = LocalAppDimensions.current.paddingMedium,
                vertical = LocalAppDimensions.current.paddingLarge
            ),
        verticalArrangement = Arrangement.spacedBy(LocalAppSpacing.current.md)
    ) {
        Spacer(modifier = Modifier.height(LocalAppSpacing.current.xl))

        LoanText(
            text = "Welcome",
            fontSize = LocalAppTextSizes.current.extraLarge
        )

        LoanTextField(
            value = uiState.email,
            onValueChange = {
                actions.onEmailChanged(value = it)
            },
            label = "Email",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            validators = emailValidators
        )

        LoanTextField(
            value = uiState.password,
            onValueChange = {
                actions.onPasswordChanged(value = it)
            },
            label = "Password",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            validators = passwordValidators
        )

        Spacer(modifier = Modifier.height(LocalAppSpacing.current.md))

        LoanButton(
            onClick = actions::onLogin,
            enabled = uiState.isLoginButtonEnabled
        ) {
            LoanText(text = "Login")
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(
        uiState = LoginUiState(),
        actions = object : LoginActions {
            override fun onEmailChanged(value: LoanTextFieldValue) = Unit
            override fun onPasswordChanged(value: LoanTextFieldValue) = Unit
            override fun onLogin() = Unit
        }
    )
}
