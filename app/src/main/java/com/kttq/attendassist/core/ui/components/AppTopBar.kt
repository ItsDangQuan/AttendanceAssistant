package com.kttq.attendassist.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCenterAlignedTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    @DrawableRes navigationIconRes: Int? = null,
    navigationIconContentDescription: String? = null,
    @DrawableRes actionIconRes: Int? = null,
    actionIconContentDescription: String? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { if (title != null)  Text(text = title) },
        navigationIcon = {
            if(navigationIconRes != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        painter = painterResource(navigationIconRes),
                        contentDescription = navigationIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        actions = {
            if(actionIconRes != null) {
                IconButton(onClick = onActionClick) {
                    Icon(
                        painter = painterResource(actionIconRes),
                        contentDescription = actionIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        colors = colors,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AppCenterAlignedTopBarPreview() {
    AttendanceAssistantTheme {
        AppCenterAlignedTopBar(
            title = "Preview",
            navigationIconRes = R.drawable.ic_arrow_back,
            actionIconRes = R.drawable.ic_account_circle,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    @DrawableRes navigationIconRes: Int? = null,
    navigationIconContentDescription: String? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    onNavigationClick: () -> Unit = {},
) {
    TopAppBar(
        title = { if (title != null)  Text(text = title) },
        navigationIcon = {
            if(navigationIconRes != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        painter = painterResource(navigationIconRes),
                        contentDescription = navigationIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        actions = actions,
        colors = colors,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AppTopBarPreview() {
    AttendanceAssistantTheme {
        AppTopBar(
            title = "Preview",
            navigationIconRes = R.drawable.ic_arrow_back,
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_account_circle),
                        contentDescription = "Account",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        )
    }
}
