package com.buntupana.tmdb.core.ui.composables.seerr

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.SeerrStatus
import com.panabuntu.tmdb.core.common.provider.SeerrStatusProvider
import org.koin.compose.koinInject

@Composable
fun SeerrStatusBadge(
    modifier: Modifier = Modifier,
    mediaType: MediaType,
    tmdbId: Long,
    statusProvider: SeerrStatusProvider = koinInject()
) {
    val isConnected by statusProvider.isConnected.collectAsStateWithLifecycle()
    if (!isConnected) return

    val status by statusProvider.statusFor(mediaType, tmdbId).collectAsStateWithLifecycle()

    SeerrStatusBadge(
        modifier = modifier,
        status = status,
    )
}

@Composable
fun SeerrStatusBadge(
    modifier: Modifier = Modifier,
    status: SeerrStatus?,
) {

    AnimatedVisibility(
        modifier = modifier,
        visible = status != null,
        enter = fadeIn()
    ) {

        val statusIconRes = getSeerIcon(status)

        statusIconRes ?: return@AnimatedVisibility

        Image(
            painter = painterResource(statusIconRes),
            contentDescription = null
        )
    }
}

@Composable
private fun getSeerIcon(status: SeerrStatus?): Int? {
    return when (status) {
        SeerrStatus.AVAILABLE -> R.drawable.ic_seerr_available
        SeerrStatus.PARTIALLY_AVAILABLE -> R.drawable.ic_seerr_parcially_available
        SeerrStatus.REQUESTED -> R.drawable.ic_seerr_requested
        SeerrStatus.PENDING -> R.drawable.ic_seerr_pending
        SeerrStatus.UNKNOWN, SeerrStatus.DELETED -> null
        else -> null
    }
}

@Preview
@Composable
private fun SeerStatusBadgePreview() {
    AppTheme {
        SeerrStatusBadge(
            status = SeerrStatus.AVAILABLE
        )
    }
}
