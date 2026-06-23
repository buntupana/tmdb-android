package com.buntupana.tmdb.core.ui.composables.seerr

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.panabuntu.tmdb.core.common.model.SeerrStatus

@Composable
fun SeerrStatusLabel(
    modifier: Modifier = Modifier,
    status: SeerrStatus?,
) {
    val resolved = status ?: return

    val (label, color) = labelAndColor(resolved)
    if (label == null) return

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(colorResource(color.colorResId))
            .border(1.dp, colorResource(color.colorLightResId), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = colorResource(color.colorTransparentResId),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

enum class SeerrLabelColors(
    val colorResId: Int,
    val colorLightResId: Int,
    val colorTransparentResId: Int
) {
    DELETED(
        colorResId = R.color.seerr_status_rejected,
        colorLightResId = R.color.seerr_status_rejected_light,
        colorTransparentResId = R.color.seerr_status_rejected_transparent
    ),
    PENDING(
        colorResId = R.color.seerr_status_pending,
        colorLightResId = R.color.seerr_status_pending_light,
        colorTransparentResId = R.color.seerr_status_pending_transparent
    ),
    REQUESTED(
        colorResId = R.color.seerr_status_requested,
        colorLightResId = R.color.seerr_status_requested_light,
        colorTransparentResId = R.color.seerr_status_requested_transparent
    ),
    PARTIALLY_AVAILABLE(
        colorResId = R.color.seerr_status_available,
        colorLightResId = R.color.seerr_status_available_light,
        colorTransparentResId = R.color.seerr_status_available_transparent
    ),
    AVAILABLE(
        colorResId = R.color.seerr_status_available,
        colorLightResId = R.color.seerr_status_available_light,
        colorTransparentResId = R.color.seerr_status_available_transparent
    );
}

@Composable
private fun labelAndColor(status: SeerrStatus): Pair<String?, SeerrLabelColors> {
    return when (status) {
        SeerrStatus.AVAILABLE -> stringResource(R.string.seerr_status_available) to SeerrLabelColors.AVAILABLE

        SeerrStatus.PARTIALLY_AVAILABLE -> stringResource(R.string.seerr_status_partially_available) to SeerrLabelColors.PARTIALLY_AVAILABLE

        SeerrStatus.REQUESTED -> stringResource(R.string.seerr_status_requested) to SeerrLabelColors.REQUESTED

        SeerrStatus.UNKNOWN -> stringResource(R.string.seerr_status_deleted) to SeerrLabelColors.DELETED

        SeerrStatus.PENDING -> stringResource(R.string.seerr_status_pending) to SeerrLabelColors.PENDING

        SeerrStatus.DELETED -> stringResource(R.string.seerr_status_deleted) to SeerrLabelColors.DELETED
    }
}

@Preview
@Composable
private fun SeerrStatusBadgePreview() {
    AppTheme {
        SeerrStatusLabel(
            status = SeerrStatus.REQUESTED
        )
    }
}
