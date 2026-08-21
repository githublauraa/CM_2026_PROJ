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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R



enum class DiscoverTab(val iconRes: Int) {
    HOME(R.drawable.ic_home),
    SEARCH(R.drawable.ic_search),
    FAVORITES(R.drawable.ic_star),
    PROFILE(R.drawable.ic_profile)
}

@Composable
fun DiscoverBottomNavigationBar(
    selectedTab: DiscoverTab,
    onTabSelected: (DiscoverTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val barColor = colorResource(R.color.voxel_background).copy(alpha = 0.8f)

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
            DiscoverTab.entries.forEach { tab ->
                val isSelected = tab == selectedTab

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = { onTabSelected(tab) }
                    ) {
                        Icon(
                            painter = painterResource(id = tab.iconRes),
                            contentDescription = tab.name,
                            tint = if (isSelected) colorResource(R.color.voxel_secondary) else colorResource(R.color.voxel_accent_purple)
                        )
                    }

                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(
                                    color = colorResource(R.color.voxel_secondary),
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun DiscoverBottomNavigationBarPreview() {
    DiscoverBottomNavigationBar(
        selectedTab = DiscoverTab.SEARCH,
        onTabSelected = {}
    )
}
