package com.buntupana.tmdb.app.presentation.home

import androidx.compose.ui.graphics.vector.ImageVector
import com.buntupana.tmdb.app.presentation.navigation.Routes

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector = selectedIcon,
    val isSelected: Boolean,
    val route: Routes
)