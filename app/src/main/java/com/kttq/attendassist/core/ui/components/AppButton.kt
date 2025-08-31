package com.kttq.attendassist.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kttq.attendassist.R

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Sign In button
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.button_height)),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            fontSize = dimensionResource(R.dimen.text_xl).value.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppButtonPreview() {
    AppButton(
        onClick = {},
        text = "Log In",
    )
}

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = dimensionResource(R.dimen.text_base),
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minWidth = 0.dp, minHeight = 0.dp),
        contentPadding = PaddingValues(0.dp) // ⬅ removes button padding
    ) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = size.value.sp,
                lineHeight = size.value.sp
            ),
        )
    }
}



@Preview(showBackground = true)
@Composable
private fun AppTextButtonPreview() {
    AppTextButton(
        onClick = {},
        text = "Forgot password?",
    )
}