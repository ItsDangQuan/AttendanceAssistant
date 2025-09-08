package com.kttq.attendassist.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun AppHorizontalCard(
    header: String,
    modifier: Modifier = Modifier,
    subHeader: String? = null,
    avatarLetter: String = "A",
    @DrawableRes coverImageRes: Int? = null
) {
    Card(
        modifier = modifier,
    ) {

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.avatar_xl))
                    .padding(dimensionResource(R.dimen.padding_small))
                    .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = avatarLetter,
                )
            }
            // Text column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(R.dimen.padding_small)),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AppBodyPrimary(header)
                if (subHeader != null) AppBodySecondary(subHeader)
            }
            //TODO: Prefer using AsyncImage instead
            Image(
                painter = painterResource(coverImageRes ?: R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.avatar_xl))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppHorizontalCardPreview() {
    AttendanceAssistantTheme {
        AppHorizontalCard(
            header = "Preview",
            subHeader = "Sub header"
        )
    }
}


@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = content
        )
    }
}



@Preview
@Composable
private fun AppCardPreview() {
    AttendanceAssistantTheme {
        AppCard {
            Text("Preview")
        }
    }
}