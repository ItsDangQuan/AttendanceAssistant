package com.kttq.attendassist.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kttq.attendassist.R


@Composable
fun AppAvatarImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.ic_image), // replace with profile image
        contentDescription = "Profile Picture",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
    )
}

@Composable
fun AppAvatarLetter(
    letter: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f) // ensure perfect circle
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.take(1).uppercase(), // only 1 letter, capitalized
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)).wrapContentSize()
        )
    }
}

