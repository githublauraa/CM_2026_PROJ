package com.example.voxel_review.ui.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.voxel_review.R
import com.example.voxel_review.navigation.AppScreen
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

internal val LocalBottomBarPadding = staticCompositionLocalOf { 0.dp }

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
    BottomNavItem(
        AppScreen.Chats.route,
        R.string.nav_chats,
        Icons.AutoMirrored.Filled.Chat,
        Icons.AutoMirrored.Outlined.Chat
    ),
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
        indicatorColor = Color.Transparent,
        unselectedIconColor = onSurfaceVariantDark,
        unselectedTextColor = onSurfaceVariantDark
    )

    val labelSize = remember {
        TextAutoSize.StepBased(minFontSize = 9.sp, maxFontSize = 12.sp, stepSize = 0.5.sp)
    }

    val barShape = RoundedCornerShape(28.dp)
    val glassTint = Brush.verticalGradient(
        listOf(VoxelSurfaceVariant.copy(alpha = 0.82f), VoxelSurface.copy(alpha = 0.93f))
    )
    val glassEdge = Brush.verticalGradient(
        listOf(Color.White.copy(alpha = 0.30f), Color.White.copy(alpha = 0.10f))
    )

    NavigationBar(
        modifier = modifier
            .navigationBarsPadding()
            .padding(horizontal = 6.dp, vertical = 6.dp)
            .clip(barShape)
            .background(glassTint)
            .border(1.dp, glassEdge, barShape),
        containerColor = Color.Transparent,
        tonalElevation = 0.dp,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
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
                    BottomNavIcon(
                        item = item,
                        selected = selected
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.label),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontFamily = FontFamily.SansSerif,
                            letterSpacing = 0.sp,
                            lineHeight = 14.sp
                        ),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        autoSize = labelSize
                    )
                },
                colors = colors,
                modifier = Modifier.testTag("nav_${item.route}")
            )
        }
    }
}

@Composable
private fun BottomNavIcon(
    item: BottomNavItem,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val iconScale by animateFloatAsState(
        targetValue = if (selected) 1.12f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "Navigation icon scale"
    )

    Icon(
        modifier = modifier
            .size(24.dp)
            .graphicsLayer {
                scaleX = iconScale
                scaleY = iconScale
            },
        imageVector = if (selected) item.filledIcon else item.outlinedIcon,
        contentDescription = null,
        tint = when {
            selected -> VoxelSecondary
            item.route == AppScreen.Chats.route -> Color.White
            else -> onSurfaceVariantDark
        }
    )
}
