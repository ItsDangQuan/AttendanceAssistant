package com.kttq.attendassist.core.ui.components

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.uiMeta
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme


@Composable
fun AppBottomBar(
    destinations: List<Destination>,
    isSelected: (Destination) -> Boolean,
    onDestinationSelected: (Destination) -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("AppBottomBar", "AppBottomBar recomposed")

    // Keep an up-to-date reference to the click lambda
    val onSelectedState by rememberUpdatedState(onDestinationSelected)

    NavigationBar(
        modifier = modifier,
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        destinations.forEach { destination ->
            key(destination) {
                // uiMeta() is non-composable — safe to remember
                val meta = remember(destination) { destination.uiMeta() }
                val selected = isSelected(destination)

                // Choose resource id (non-composable)
                val iconResId = if (selected) meta.selectedIconRes!! else meta.unselectedIconRes!!

                // painterResource is @Composable — call it here (not inside remember { }).
                val painter = painterResource(iconResId)

                // stringResource is @Composable — call it directly here.
                val contentDescription = meta.titleTextRes?.let { stringResource(it) }

                NavigationBarItem(
                    selected = selected,
                    onClick = { onSelectedState(destination) },
                    icon = {
                        Icon(
                            painter = painter,
                            contentDescription = contentDescription
                        )
                    }
                )
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
private fun AppBottomBarPreview() {
    AttendanceAssistantTheme {
        AppBottomBar(
            destinations = listOf(Destination.Teacher.Home),
            isSelected = { true },
            onDestinationSelected = {}
        )
    }
}