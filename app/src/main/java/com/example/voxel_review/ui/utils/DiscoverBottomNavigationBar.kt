package com.example.voxel_review.ui.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.voxel_review.R
import com.example.voxel_review.navigation.AppScreen
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

private data class BottomNavItem(
    val route: String,
    val label: Int,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
)

private val bottomNavItems = listOf(
    BottomNavItem(AppScreen.RankingsUser.route, R.string.nav_rankings, Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(AppScreen.Discover.route, R.string.nav_discover, Icons.Filled.Search, Icons.Outlined.Search),
    BottomNavItem(AppScreen.Reviews.route, R.string.nav_reviews, Icons.Filled.Star, Icons.Outlined.Star),
    BottomNavItem(AppScreen.PerfilUser.route, R.string.nav_profile, Icons.Filled.Person, Icons.Outlined.Person)
)

@Composable
fun DiscoverBottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val entry by navController.currentBackStackEntryAsState()
    val currentRoute = entry?.destination?.route?.substringBefore("?")
    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = VoxelSecondary,
        selectedTextColor = VoxelSecondary,
        indicatorColor = VoxelSurfaceVariant,
        unselectedIconColor = onSurfaceVariantDark,
        unselectedTextColor = onSurfaceVariantDark
    )

    Column(modifier) {
        HorizontalDivider(color = VoxelBorder)
        NavigationBar(containerColor = VoxelSurface, tonalElevation = 0.dp) {
            bottomNavItems.forEach { item ->
                val selected = currentRoute == item.route
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            navController.navigate(item.route) { launchSingleTop = true }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (selected) item.filledIcon else item.outlinedIcon,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(item.label), maxLines = 1) },
                    colors = colors,
                    modifier = Modifier.testTag("nav_${item.route}")
                )
            }
        }
    }
}
