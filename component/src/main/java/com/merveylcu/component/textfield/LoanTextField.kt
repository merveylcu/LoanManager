package com.merveylcu.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.merveylcu.component.text.LoanText
import com.merveylcu.component.textfield.validator.LoanTextFieldStringValidator
import com.merveylcu.core.theme.LocalAppColors
import com.merveylcu.core.theme.LocalAppDimensions
import com.merveylcu.core.theme.LocalAppTextSizes
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DEFAULT_MAX_LENGTH = 255
private const val ERROR_DELAY = 400L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanTextField(
    modifier: Modifier = Modifier,
    value: LoanTextFieldValue,
    onValueChange: (LoanTextFieldValue) -> Unit,
    enabled: Boolean = true,
    hasDisabledBackground: Boolean = false,
    isBorderless: Boolean = false,
    readOnly: Boolean = false,
    labelModifier: Modifier = Modifier,
    label: String? = null,
    helperText: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    startIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    minHeight: Dp = LocalAppDimensions.current.textFieldHeight,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = loanTextFieldColors(hasDisabledBackground),
    validators: List<LoanTextFieldStringValidator> = emptyList(),
    maxLength: Int = DEFAULT_MAX_LENGTH,
    contentPadding: PaddingValues = TextFieldDefaults.contentPaddingWithLabel(top = LocalAppDimensions.current.paddingSmall),
    hasClearIcon: Boolean = false
) {
    var textIsChanged by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var job by remember { mutableStateOf<Job?>(null) }

    val isFocused by interactionSource.collectIsFocusedAsState()
    var showError by remember { mutableStateOf(false) }

    val cursorColor = LocalAppColors.current.primary
    val cornerShape = RoundedCornerShape(LocalAppDimensions.current.cornerRadiusMedium)

    val textSelectionColors = TextSelectionColors(
        handleColor = cursorColor,
        backgroundColor = LocalAppColors.current.passive
    )

    val endIconOrDefault: @Composable (() -> Unit)? = when {
        endIcon != null -> endIcon
        hasClearIcon && isFocused && value.text.isNotEmpty() -> {
            {
                LoanTextFieldClearIcon(
                    onClick = {
                        val emptyInput = ""
                        val error = validators.find { it.validate(emptyInput).not() }
                        onValueChange(
                            LoanTextFieldValue(
                                text = emptyInput,
                                isError = error != null,
                                errorMessage = error?.errorMessage.orEmpty()
                            )
                        )
                    }
                )
            }
        }

        else -> null
    }

    LaunchedEffect(value.isError) {
        job?.cancel()
        job = coroutineScope.launch {
            delay(ERROR_DELAY)
            showError = value.isError
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            job?.cancel()
        }
    }

    LaunchedEffect(validators) {
        if (textIsChanged) {
            onValueChanged(
                validators = validators,
                textFieldValue = value,
                text = value.text,
                onValueChange = onValueChange
            )
        }
    }

    LoanTextFieldTheme {
        Column(
            modifier = modifier
        ) {
            CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
                val borderColor = loanTextFieldBorderColor(
                    isError = showError,
                    isFocus = isFocused,
                    hasDisabledBackground = hasDisabledBackground,
                    isBorderless = isBorderless
                )

                BasicTextField(
                    value = value.text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = minHeight)
                        .border(
                            width = LocalAppDimensions.current.borderWidth,
                            color = borderColor.value,
                            shape = cornerShape

                        )
                        .clip(cornerShape),
                    onValueChange = { newValue ->
                        if (newValue.length > maxLength) return@BasicTextField
                        textIsChanged = true

                        onValueChanged(
                            validators = validators,
                            textFieldValue = value,
                            text = newValue,
                            onValueChange = onValueChange
                        )
                    },
                    enabled = enabled,
                    readOnly = readOnly,
                    textStyle = TextStyle(
                        fontSize = LocalAppTextSizes.current.medium,
                        fontWeight = FontWeight.Normal,
                        color = LocalAppColors.current.primary
                    ),
                    cursorBrush = SolidColor(cursorColor),
                    visualTransformation = visualTransformation,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    interactionSource = interactionSource,
                    singleLine = singleLine,
                    maxLines = maxLines,
                    minLines = minLines,
                    decorationBox = @Composable { innerTextField ->
                        TextFieldDefaults.DecorationBox(
                            value = value.text,
                            visualTransformation = visualTransformation,
                            innerTextField = innerTextField,
                            placeholder = placeholder,
                            leadingIcon = startIcon,
                            trailingIcon = endIconOrDefault,
                            prefix = prefix,
                            suffix = suffix,
                            supportingText = supportingText,
                            label = label?.let {
                                {
                                    LoanText(
                                        modifier = labelModifier,
                                        text = it,
                                        singleLine = singleLine
                                    )
                                }
                            },
                            singleLine = singleLine,
                            enabled = enabled,
                            isError = showError,
                            interactionSource = interactionSource,
                            colors = colors,
                            contentPadding = contentPadding
                        )
                    }
                )
            }

            if (showError && value.errorMessage.isNotEmpty()) {
                LoanTextFieldErrorHelper(
                    errorMessage = value.errorMessage
                )
            }

            helperText?.takeIf { it.isNotEmpty() && showError.not() }?.let { text ->
                LoanTextFieldErrorHelper(
                    errorMessage = text,
                    componentColor = LocalAppColors.current.passive
                )
            }
        }
    }
}

private fun onValueChanged(
    validators: List<LoanTextFieldStringValidator>,
    textFieldValue: LoanTextFieldValue,
    text: String,
    onValueChange: (LoanTextFieldValue) -> Unit
) {
    var errorMessage = ""
    var isError = false

    if (validators.isNotEmpty()) {
        val notValidValidator = validators.find { validator ->
            validator.validate(text.trim()).not()
        }
        errorMessage = notValidValidator?.errorMessage.orEmpty()
        isError = notValidValidator != null
    }
    onValueChange.invoke(
        textFieldValue.copy(
            text = text,
            isError = isError,
            errorMessage = errorMessage
        )
    )
}

@Composable
private fun LoanTextFieldErrorHelper(
    errorMessage: String,
    componentColor: Color = LocalAppColors.current.error
) {
    Row(
        modifier = Modifier.padding(top = LocalAppDimensions.current.paddingLarge),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(LocalAppDimensions.current.paddingMedium)
    ) {
        Icon(
            modifier = Modifier.padding(top = LocalAppDimensions.current.paddingSmall),
            painter = painterResource(id = android.R.drawable.stat_sys_warning),
            tint = componentColor,
            contentDescription = null
        )
        LoanText(
            text = errorMessage,
            fontSize = LocalAppTextSizes.current.medium,
            lineHeight = LocalAppTextSizes.current.extraLarge,
            color = componentColor
        )
    }
}

@Composable
private fun LoanTextFieldClearIcon(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Icon(
        modifier = modifier.clickable(onClick = onClick),
        painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
        contentDescription = "clear"
    )
}
