package com.merveylcu.component.button

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import com.merveylcu.core.theme.LocalAppColors

@Composable
internal fun ButtonDefaults.primaryButtonColors(): ButtonColors {
    val backgroundColor = LocalAppColors.current.primary
    val disabledBackgroundColor = LocalAppColors.current.passive
    val textColor = LocalAppColors.current.textPrimary

    return buttonColors(
        containerColor = backgroundColor,
        disabledContainerColor = disabledBackgroundColor,
        contentColor = textColor
    )
}

@Composable
internal fun ButtonDefaults.secondaryButtonColors(): ButtonColors {
    val backgroundColor = LocalAppColors.current.secondary
    val disabledBackgroundColor = LocalAppColors.current.passive
    val textColor = LocalAppColors.current.textPrimary

    return buttonColors(
        containerColor = backgroundColor,
        disabledContainerColor = disabledBackgroundColor,
        contentColor = textColor
    )
}
