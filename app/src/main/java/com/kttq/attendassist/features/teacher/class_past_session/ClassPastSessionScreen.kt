package com.kttq.attendassist.features.teacher.class_past_session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.model.TeacherSession
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun ClassPastSessionRoute(
    modifier: Modifier = Modifier,
    viewModel: ClassPastSessionViewModel = hiltViewModel()
) {
    ClassPastSessionScreen(
        viewModel.sessionList.collectAsStateWithLifecycle().value,
        modifier = modifier
    )
}

@Composable
fun ClassPastSessionScreen(
    sessionList: List<TeacherSession>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        LazyColumn(
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items(sessionList) { session ->
                AppCard {
                    AppLabelPrimary(session.sessionId)
                    AppLabelPrimary(session.endTime)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClassPastSessionScreenPreview() {
    AttendanceAssistantTheme {
        ClassPastSessionScreen(
            sessionList = emptyList()
        )
    }

}