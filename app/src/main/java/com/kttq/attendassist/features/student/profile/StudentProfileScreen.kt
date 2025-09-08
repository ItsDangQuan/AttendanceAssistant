package com.kttq.attendassist.features.student.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
// Removed App import as it's unused after changes
import com.kttq.attendassist.core.ui.components.AppAvatarImage
import com.kttq.attendassist.core.ui.components.AppIconButton
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppOutlineTextField
import com.kttq.attendassist.core.ui.components.AppScreenTitle
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.components.AppSubsectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun StudentProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: StudentProfileViewModel = hiltViewModel() // Renamed for clarity
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_large))
    ) {
        AppAvatarImage() // Assuming this doesn't need data from ViewModel for now

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_8)))

        AppScreenTitle(uiState.name.ifEmpty { "Loading..." }) // Display name from uiState
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
            value = uiState.name,
            onValueChange = {}, // Assuming read-only for now
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        AppSubsectionTitle("Student ID", modifier = Modifier.fillMaxWidth())
        AppOutlineTextField(
            label = "",
            value = uiState.studentId,
            onValueChange = {}, // Assuming read-only for now
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        AppSubsectionTitle("Email", modifier = Modifier.fillMaxWidth())
        AppOutlineTextField(
            label = "",
            value = uiState.email,
            onValueChange = {}, // Assuming read-only for now
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        AppSubsectionTitle("Phone", modifier = Modifier.fillMaxWidth())
        AppOutlineTextField(
            label = "", // Changed from "Phone" as AppSubsectionTitle is present
            value = uiState.phone,
            onValueChange = {}, // Assuming read-only for now
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_24)))
        AppSectionTitle("Settings", Modifier.fillMaxWidth())
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppLabelPrimary("Change password", Modifier.weight(1f))
            AppIconButton(
                iconId = R.drawable.ic_arrow_forward,
                onClick = { viewModel.onChangePasswordClicked() } // Call ViewModel function
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StudentProfileScreenPreview() {
    AttendanceAssistantTheme {
        // Preview will use Hilt to provide the ViewModel.
        // For more complex scenarios, consider providing a mock ViewModel or UiState.
        StudentProfileScreen()
    }
}
