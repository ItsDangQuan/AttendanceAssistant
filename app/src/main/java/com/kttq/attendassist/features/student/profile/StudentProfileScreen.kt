package com.kttq.attendassist.features.student.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.App
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
    studentProfileViewModel: StudentProfileViewModel = hiltViewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_large))
    ) {
        AppAvatarImage()

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_8)))

        // TODO: Replace username by real username
        AppScreenTitle("Tu Thanh")
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_24)))

        // Personal Information Section
        AppSectionTitle("Personal Information",
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_12)))


        AppSubsectionTitle("Name", modifier = Modifier.fillMaxWidth())
        AppOutlineTextField(
            label = "",
            value = "Tu Thanh",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readable = true
        )
        AppSubsectionTitle("Student ID", modifier = Modifier.fillMaxWidth())
        AppOutlineTextField(
            label = "",
            value = "23125018",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readable = true
        )
        AppSubsectionTitle("Email", modifier = Modifier.fillMaxWidth())
        AppOutlineTextField(
            label = "",
            value = "tuthanh10825@gmail.com",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readable = true
        )
        AppSubsectionTitle("Phone", modifier = Modifier.fillMaxWidth())
        AppOutlineTextField(
            label = "Phone",
            value = "0123456789",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readable = true
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
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StudentProfileScreenPreview() {
    AttendanceAssistantTheme {
        StudentProfileScreen()
    }
}