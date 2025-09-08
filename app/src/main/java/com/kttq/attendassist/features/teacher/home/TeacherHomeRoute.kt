package com.kttq.attendassist.features.teacher.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.components.AppIconButton
import com.kttq.attendassist.core.ui.components.AppScreenTitle
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun TeacherHomeRoute(
    onSentToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onSentToBack()
    }
    TeacherHomeScreen(modifier = modifier)
}

@Composable
fun TeacherHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: TeacherHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AppScreenTitle("Hello, ${uiState.userName}")
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppSectionTitle("Current Checking")
            AppIconButton(
                iconId = R.drawable.ic_arrow_forward,
                onClick = {

                },
                modifier = Modifier.wrapContentSize()
            )
        }
        LazyColumn {
            //TODO: Add list of current class and incoming classes based on uiState
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppSectionTitle("Class Today")
            AppIconButton(
                iconId = R.drawable.ic_arrow_forward,
                onClick = {

                },
                modifier = Modifier.wrapContentSize()
            )
        }
        LazyColumn {
            //TODO: Add list of current class and incoming classes based on uiState
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TeacherHomeScreenPreview() {
    AttendanceAssistantTheme {
        // The preview will attempt to use Hilt to create the ViewModel.
        // For complex ViewModels, this might require additional Hilt setup for previews
        // or providing a mock ViewModel/UiState directly to TeacherHomeScreen.
        TeacherHomeScreen()
    }
}

