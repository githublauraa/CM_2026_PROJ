package com.example.voxel_review.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.voxel_review.R
import com.example.voxel_review.navigation.AppScreen


data class BottomNavItem(
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val route: String
)


val bottomNavItems = listOf(

    BottomNavItem(
        filledIcon = Icons.Filled.Home,
        outlinedIcon = Icons.Outlined.Home,
        route = AppScreen.RankingsUser.route
    ),

    BottomNavItem(
        filledIcon = Icons.Filled.Search,
        outlinedIcon = Icons.Outlined.Search,
        route = AppScreen.Discover.route
    ),

    BottomNavItem(
        filledIcon = Icons.Filled.Star,
        outlinedIcon = Icons.Outlined.Star,
        route = AppScreen.WriteReview.route
    ),

    BottomNavItem(
        filledIcon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person,
        route = AppScreen.Configuration.route
    )
)


@Composable
fun DiscoverBottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val navBackStackEntry =
        navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry.value?.destination?.route


    val barColor =
        colorResource(R.color.voxel_background)
            .copy(alpha = 0.8f)


    BottomAppBar(
        modifier = modifier.height(70.dp),
        containerColor = barColor,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            bottomNavItems.forEach { item ->

                val isSelected =
                    currentRoute == item.route


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    IconButton(
                        onClick = {
                            navController.navigate(item.route)
                        }
                    ) {

                        Icon(
                            imageVector =
                                if (isSelected) {
                                    item.filledIcon
                                } else {
                                    item.outlinedIcon
                                },

                            contentDescription = item.route,

                            tint =
                                if (isSelected) {
                                    colorResource(
                                        R.color.voxel_secondary
                                    )
                                } else {
                                    colorResource(
                                        R.color.voxel_accent_purple
                                    )
                                }
                        )
                    }


                    if (isSelected) {

                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(
                                    color = colorResource(
                                        R.color.voxel_secondary
                                    ),
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}