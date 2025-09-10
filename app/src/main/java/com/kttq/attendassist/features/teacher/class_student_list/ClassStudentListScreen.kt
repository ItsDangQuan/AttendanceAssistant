package com.kttq.attendassist.features.teacher.class_student_list

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.data.network.models.Student
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppLabelPrimary

@Composable
fun ClassStudentListRoute(
    modifier: Modifier = Modifier,
    viewModel: ClassStudentListViewModel = hiltViewModel()
) {
    ClassStudentListScreen(
        studentsList = viewModel.studentsList.collectAsStateWithLifecycle().value,
        modifier = modifier
    )
}

@Composable
fun ClassStudentListScreen(
    studentsList: List<Student>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_medium))
    ) {
        LazyColumn (
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ){
            items(studentsList) { student ->
                AppCard {
                    AppLabelPrimary(text = student.name)
                    AppLabelPrimary(text = student.studentId)
                }
            }

        }
    }
}