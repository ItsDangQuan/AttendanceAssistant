package com.kttq.attendassist.features.student.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.components.AppIconButton
import com.kttq.attendassist.core.ui.components.AppScreenTitle
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun StudentHomeRoute(
    onSentToBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StudentHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        onSentToBack()
    }
    StudentHomeScreen(
        userName = uiState.userName,
        onIncomingClassSeeAllClicked = viewModel::onIncomingClassSeeAllClicked,
        onClassTodaySeeAllClicked = viewModel::onClassTodaySeeAllClicked,
        modifier = modifier
    )
}

@Composable
fun StudentHomeScreen(
    userName: String,
    onIncomingClassSeeAllClicked: () -> Unit,
    onClassTodaySeeAllClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_medium))
    ) {
        AppScreenTitle("Hello, $userName")
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppSectionTitle("Incoming Class")
            AppIconButton(
                iconId = R.drawable.ic_arrow_forward,
                onClick = {
                    onIncomingClassSeeAllClicked()
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
                    onClassTodaySeeAllClicked()
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
private fun StudentHomeScreenPreview() {
    AttendanceAssistantTheme {
        // The preview will attempt to use Hilt to create the ViewModel.
        // For complex ViewModels, this might require additional Hilt setup for previews
        // or providing a mock ViewModel/UiState directly to StudentHomeScreen.
        StudentHomeScreen(
            userName = "",
            onIncomingClassSeeAllClicked = {},
            onClassTodaySeeAllClicked = {}
        )
    }
}
