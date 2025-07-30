package com.merveylcu.feature.loan.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.merveylcu.component.button.LoanButton
import com.merveylcu.component.text.LoanText
import com.merveylcu.core.theme.LocalAppDimensions
import com.merveylcu.core.theme.LocalAppSpacing
import com.merveylcu.core.theme.LocalAppTextSizes
import com.merveylcu.feature.loan.presentation.compose.LoanCard

@Composable
internal fun LoanRootScreen(
    viewModel: LoanViewModel = hiltViewModel<LoanViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        viewModel.initPage()
    }

    LoanScreen(
        uiState = uiState,
        actions = viewModel
    )
}

@Composable
internal fun LoanScreen(
    uiState: LoanUiState,
    actions: LoanActions
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = LocalAppDimensions.current.paddingMedium),
        contentPadding = PaddingValues(vertical = LocalAppDimensions.current.paddingLarge),
        verticalArrangement = Arrangement.spacedBy(LocalAppDimensions.current.paddingMedium)
    ) {
        item(key = "header_title") {
            Spacer(modifier = Modifier.height(LocalAppSpacing.current.xl))

            LoanText(
                text = "Loans",
                fontSize = LocalAppTextSizes.current.extraLarge
            )
        }

        items(
            items = uiState.loans,
            key = { loan -> loan.id }
        ) { loan ->
            LoanCard(loan = loan)
        }

        item(key = "apply_button") {
            Spacer(modifier = Modifier.height(LocalAppSpacing.current.md))

            LoanButton(
                onClick = actions::onLogout
            ) {
                LoanText(text = "Logout")
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewLoanScreen() {
    LoanScreen(
        uiState = LoanUiState(),
        actions = object : LoanActions {
            override fun onLogout() = Unit
        }
    )
}
