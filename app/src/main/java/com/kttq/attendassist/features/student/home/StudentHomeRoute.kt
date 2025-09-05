package com.kttq.attendassist.features.student.home

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StudentHomeRoute(
    onSentToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onSentToBack()
    }
}