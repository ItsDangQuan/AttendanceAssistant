package com.kttq.attendassist.core.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
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
    NavigationBar(
        modifier = modifier,
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        destinations.forEach { destination ->
            val meta = destination.uiMeta()
            val selected = isSelected(destination)
            val contentDescription = meta.titleTextRes?.let { stringResource(it) }
            NavigationBarItem(
                selected = selected,
                onClick = { onDestinationSelected(destination) },
                icon = {
                    Icon(
                        painter = painterResource(
                            if (selected) meta.selectedIconRes!! else meta.unselectedIconRes!!
                        ),
                        contentDescription = contentDescription
                    )
                }
            )
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