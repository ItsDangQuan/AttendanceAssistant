package com.kttq.attendassist.features.student.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.components.AppIconButton
import com.kttq.attendassist.core.ui.components.AppScreenTitle
import com.kttq.attendassist.core.ui.components.AppSectionTitle
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
    modifier: Modifier = Modifier,
    studentViewModel: StudentHomeViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ){
        AppScreenTitle("Hello, User")
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppSectionTitle("Incoming Class")
            AppIconButton(
                iconId = R.drawable.ic_arrow_forward,
                onClick = {
                    // TODO: Handling the navigation to some destination.
                },
                modifier = Modifier.wrapContentSize()
            )
        }
        LazyColumn {
            //TODO: Add list of current class and incoming classes
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppSectionTitle("Class Today")
            AppIconButton(
                iconId = R.drawable.ic_arrow_forward,
                onClick = {
                    // TODO: Handling the navigation to some destination.
                },
                modifier = Modifier.wrapContentSize()
            )
        }
        LazyColumn {
            //TODO: Add list of current class and incoming classes
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StudentHomeScreenPreview() {
    AttendanceAssistantTheme {
        StudentHomeScreen()
    }
}
