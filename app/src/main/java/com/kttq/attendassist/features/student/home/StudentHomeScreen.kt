package com.kttq.attendassist.features.student.home

// import androidx.compose.foundation.layout.heightIn // Commented out as it's not used in current code
// import androidx.compose.foundation.lazy.LazyColumn // Commented out as it's not used in current code
// import androidx.compose.runtime.rememberCoroutineScope // Not needed here as LaunchedEffect provides a scope
// import androidx.compose.ui.unit.dp // Commented out as it's not used in current code
import android.bluetooth.le.ScanCallback
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.components.AppBodyCaption
import com.kttq.attendassist.core.ui.components.AppBodyPrimary
import com.kttq.attendassist.core.ui.components.AppBodySecondary
import com.kttq.attendassist.core.ui.components.AppButton
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppIconButton
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppLabelSecondary
import com.kttq.attendassist.core.ui.components.AppScreenTitle
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun StudentHomeRoute(
    onSentToBack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToNewSession: (Int) -> Unit,

    modifier: Modifier = Modifier,
    navigateToSummary: () -> Unit = {},
    viewModel: StudentHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val statSummary by viewModel.statSummary.collectAsStateWithLifecycle() // Collect statSummary
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val date by viewModel.formattedDate.collectAsStateWithLifecycle()
    val scanRes = viewModel.scanResult.collectAsStateWithLifecycle().value
    BackHandler {
        onSentToBack()
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            onShowSnackbar(uiState.error!!, "OK")
            viewModel.updateError(null)
        }
    }

    LaunchedEffect(scanRes) {
        if (scanRes.isNotEmpty() && uiState.isScanning) {
            Log.d("StudentHomeRoute", "Scan res size: ${scanRes.size}")
            val sessionId = scanRes.first().second
            Log.d("StudentHomeRoute", "Session ID: $sessionId")
            viewModel.stopScan()
            viewModel.clearScanResult()
            navigateToNewSession(sessionId)
        }
    }
    StudentHomeScreen(
        userName = userProfile?.firstName ?: "Loading...",
        date = date,
        statSummary = statSummary,
        isScanning = uiState.isScanning, // Pass scanning state
        onScanClicked = {
            viewModel.startScan(
                onScanSuccess = navigateToNewSession,
                onScanFailure = { errorCode ->
                    val errorMessage = when (errorCode) {
                        -1 -> "Scan failed: Scanner not available."
                        ScanCallback.SCAN_FAILED_ALREADY_STARTED -> "Scan failed: Already started."
                        ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED -> "Scan failed: Application registration failed."
                        ScanCallback.SCAN_FAILED_INTERNAL_ERROR -> "Scan failed: Internal error."
                        ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED -> "Scan failed: Feature unsupported."
                        ScanCallback.SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES -> "Scan failed: Out of hardware resources."
                        ScanCallback.SCAN_FAILED_SCANNING_TOO_FREQUENTLY -> "Scan failed: Scanning too frequently. Try again later."
                        else -> "Scan failed: Unknown error (Code: $errorCode)"
                    }
                    viewModel.updateError(errorMessage)
                }
            )
        },
        onSummaryClicked = navigateToSummary,
        modifier = modifier
    )

}

@Composable
fun StudentHomeScreen(
    userName: String,
    date: String,
    statSummary: StudentHomeStatSummary,
    isScanning: Boolean, // Added to reflect scan state in UI if needed
    onScanClicked: () -> Unit,
    onSummaryClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        AppScreenTitle("Hello, $userName")
        AppSectionTitle(date) // TODO: Maybe refactor
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_large)), // Added vertical padding
            thickness = dimensionResource(R.dimen.divider_height),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppSectionTitle(text = "Summary")
            // AppIconButton(
            //     iconId = R.drawable.ic_arrow_forward,
            //     onClick = onSummaryClicked, // Directly use the passed lambda
            //     modifier = Modifier.wrapContentSize()
            // )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Column (
            modifier = Modifier.fillMaxWidth(),
        ) {
            AppCard(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            ) {
                AppBodyPrimary("Total sessions")
                AppBodySecondary(statSummary.total.toString())
            }
            AppCard(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            ) {
                AppBodyPrimary("Attended sessions")
                AppBodySecondary(statSummary.attended.toString())
            }
            AppCard(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            ) {
                AppBodyPrimary("Absented sessions")
                AppBodySecondary(statSummary.absent.toString())
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_large)), // Added vertical padding
            thickness = dimensionResource(R.dimen.divider_height),
        )
        AppSectionTitle(text = "Checking")
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        AppButton(
            onClick = onScanClicked, // Directly use the passed lambda
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium)), // Use horizontal padding
            enabled = !isScanning
        ) {
            AppLabelPrimary(if (isScanning) "Scanning..." else "Scan for class")
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun StudentHomeScreenPreview() {
    AttendanceAssistantTheme {
        StudentHomeScreen(
            userName = "User",
            date = "",
            isScanning = false,
            statSummary = StudentHomeStatSummary(
                total = 10,
                attended = 5,
            ),
            onScanClicked = {},
            onSummaryClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StudentHomeScreenScanningPreview() {
    AttendanceAssistantTheme {
        StudentHomeScreen(
            userName = "User",
            date = "",
            statSummary = StudentHomeStatSummary(
                total = 10,
                attended = 5,
            ),
            isScanning = true,
            onScanClicked = {},
            onSummaryClicked = {}
        )
    }
}
