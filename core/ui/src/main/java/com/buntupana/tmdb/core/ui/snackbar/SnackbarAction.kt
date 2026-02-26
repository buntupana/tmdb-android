package com.buntupana.tmdb.core.ui.snackbar

data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit
)
