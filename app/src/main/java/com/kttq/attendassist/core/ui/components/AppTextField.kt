package com.kttq.attendassist.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppOutlineTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes leadingIconRes: Int? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    isVisible: Boolean = true,
    readOnly: Boolean = false
) {
    if (leadingIconRes != null) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) }, // ✅ wrap in Text
            leadingIcon = {
                Icon(
                    painter = painterResource(id = leadingIconRes),
                    contentDescription = null
                )
            },
            trailingIcon = trailingIcon,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            visualTransformation = if (isVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            modifier = modifier,
            readOnly = readOnly
        )
    }
    else {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) }, // ✅ wrap in Text
            trailingIcon = trailingIcon,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            visualTransformation = if (isVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            modifier = modifier,
            readOnly = readOnly
        )
    }
}



@Preview(showBackground = true)
@Composable
private fun AppOutlineTextFieldPreview() {
    AppOutlineTextField(
        label = "Email",
        value = "",
        onValueChange = {},
    )

}