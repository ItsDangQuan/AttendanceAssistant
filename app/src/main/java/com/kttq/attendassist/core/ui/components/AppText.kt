package com.kttq.attendassist.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

/**
 * ===============================================================
 *  📚 App Typography Hierarchy
 * ===============================================================
 *
 * Titles:
 *  - AppScreenTitle     → Main screen/page titles
 *  - AppSectionTitle    → Section headers
 *  - AppSubsectionTitle → Smaller headers
 *
 * Body:
 *  - AppBodyPrimary     → Main content
 *  - AppBodySecondary   → Supporting text
 *  - AppBodyCaption     → Metadata, notes
 *
 * Labels:
 *  - AppLabelPrimary    → Buttons, chips
 *  - AppLabelSecondary  → Hints, helper text
 *
 * ---------------------------------------------------------------
 * Usage Guideline:
 *  - Titles are for hierarchy (screen > section > subsection).
 *  - Body is for content density (primary > secondary > caption).
 *  - Labels are for functional UI text (actions, hints, tags).
 * ===============================================================
 */

/**
 * Screen title – used for main page titles
 */
@Composable
fun AppScreenTitle(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Bold, // ⬅ default, but configurable
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = fontWeight),
        modifier = modifier
    )
}

/**
 * Section title – used for grouping content inside a screen
 */
@Composable
fun AppSectionTitle(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.SemiBold,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = fontWeight),
        modifier = modifier
    )
}

/**
 * Subsection title – used for smaller headers / list titles
 */
@Composable
fun AppSubsectionTitle(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Medium,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = fontWeight),
        modifier = modifier
    )
}

/**
 * Primary body text – main content, paragraphs
 */
@Composable
fun AppBodyPrimary(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = fontWeight),
        modifier = modifier
    )
}

/**
 * Secondary body text – supporting info
 */
@Composable
fun AppBodySecondary(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = fontWeight),
        modifier = modifier
    )
}

/**
 * Caption text – metadata, timestamps
 */
@Composable
fun AppBodyCaption(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Light,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = fontWeight),
        modifier = modifier
    )
}

/**
 * Primary label – buttons, chips
 */
@Composable
fun AppLabelPrimary(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Medium,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = fontWeight),
        modifier = modifier
    )
}

/**
 * Secondary label – hints, helper text
 */
@Composable
fun AppLabelSecondary(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = fontWeight),
        modifier = modifier
    )
}
