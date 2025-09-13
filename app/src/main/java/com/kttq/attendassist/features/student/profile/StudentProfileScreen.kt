package com.kttq.attendassist.features.student.profile

// Removed App import as it's unused after changes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.components.AppButton
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppAvatarLetter
import com.kttq.attendassist.core.ui.components.AppOutlineTextField
import com.kttq.attendassist.core.ui.components.AppScreenTitle
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.components.AppSubsectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun StudentProfileRoute(
    navigateToRedirect: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StudentProfileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val user = viewModel.user.collectAsStateWithLifecycle().value

    if (user == null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    } else {
        StudentProfileScreen(
            name = user.firstName + " " + user.lastName,
            studentId = user.studentId,
            email = user.email,
            onChangePasswordClicked = viewModel::onChangePasswordClicked,
            onLogoutClicked = {
                viewModel.onLogoutClicked()
            },
            modifier = modifier
        )
    }
    LaunchedEffect(uiState.isLoggedOut) {
        if (uiState.isLoggedOut) {
            navigateToRedirect()
        }
    }
}

@Composable
fun StudentProfileScreen(
    name: String,
    studentId: String,
    email: String,
    onChangePasswordClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_large))
            .verticalScroll(rememberScrollState())
    ) {
        AppAvatarLetter(
            letter = name.first().toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .size(130.dp)
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_8)))

        AppScreenTitle(name.ifEmpty { "Loading..." }) // Display name from uiState
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_24)))

        // Personal Information Section
        AppSectionTitle(
            "Personal Information",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_12)))

        AppSubsectionTitle("Name", modifier = Modifier.fillMaxWidth())
        AppOutlineTextField(
            label = "", // Label can be empty if AppSubsectionTitle serves as label
            value = name,
            onValueChange = {}, // Assuming read-only for now
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        AppSubsectionTitle("Student ID", modifier = Modifier.fillMaxWidth())
        AppOutlineTextField(
            label = "",
            value = studentId,
            onValueChange = {}, // Assuming read-only for now
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        AppSubsectionTitle("Email", modifier = Modifier.fillMaxWidth())
        AppOutlineTextField(
            label = "",
            value = email,
            onValueChange = {}, // Assuming read-only for now
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_24)))
        AppSectionTitle("Settings", Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_12)))
        // Row(
        //     modifier = Modifier.fillMaxWidth(),
        //     verticalAlignment = Alignment.CenterVertically
        // ) {
        //     AppLabelPrimary("Change password", Modifier.weight(1f))
        //     AppIconButton(
        //         iconId = R.drawable.ic_arrow_forward,
        //         onClick = { onChangePasswordClicked() } // Call ViewModel function
        //     )
        // }
        AppButton(
            onClick = onLogoutClicked,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.error
        ) {
            AppLabelPrimary("Log out")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StudentProfileScreenPreview() {
    AttendanceAssistantTheme {
        // Preview will use Hilt to provide the ViewModel.
        // For more complex scenarios, consider providing a mock ViewModel or UiState.
        StudentProfileScreen(
            name = "",
            studentId = "",
            email = "",
            onChangePasswordClicked = {},
            onLogoutClicked = {}
        )
    }
}
