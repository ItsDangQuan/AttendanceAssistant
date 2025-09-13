package com.kttq.attendassist.features.teacher.class_session_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.model.TeacherSession
import com.kttq.attendassist.core.ui.components.AppBodyPrimary
import com.kttq.attendassist.core.ui.components.AppCard


@Composable
fun ClassSessionListRoute(
    modifier: Modifier = Modifier,
    viewModel: ClassSessionListViewModel = hiltViewModel()
) {

    val sessionList = viewModel.sessionList.collectAsStateWithLifecycle().value
    if (sessionList == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    else {
        ClassSessionListScreen(
            sessionList = sessionList
        )
    }
}

@Composable
fun ClassSessionListScreen(
    sessionList: List<TeacherSession>,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        items(sessionList) { session ->
            AppCard {
                AppBodyPrimary(text = "Session ${session.sessionId}")
                AppBodyPrimary(text = "Start time: ${session.startTime}")
            }
        }

    }
}

