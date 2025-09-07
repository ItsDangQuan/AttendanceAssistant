package com.kttq.attendassist.features.student.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun StudentHomeRoute(
    onSentToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onSentToBack()
    }
    StudentHomeScreen()
}

@Composable
fun StudentHomeScreen(
    modifier: Modifier = Modifier
) {
}

@Preview(showBackground = true)
@Composable
private fun StudentHomeScreenPreview() {
    AttendanceAssistantTheme {
        StudentHomeScreen()
    }
}