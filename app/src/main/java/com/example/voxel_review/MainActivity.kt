package com.example.voxel_review

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.screens.Discover.DiscoverScreen
import com.example.voxel_review.ui.screens.home.StartScreen
import com.example.voxel_review.ui.screens.notifications.NotificationScreen
import com.example.voxel_review.ui.screens.novedades.NovedadScreen
import com.example.voxel_review.ui.screens.profile.ProfileScreen
import com.example.voxel_review.ui.screens.rankings.RankingsScreen
import com.example.voxel_review.ui.screens.review.ReviewDetailScreen
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme
import com.example.voxel_review.ui.screens.settings.SettingsRoute
import com.example.voxel_review.ui.screens.writeReview.WriteReviewScreen
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountScreen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Voxel_ReviewTheme() {
                Scaffold(
                ) {
                    //CreateAccountScreen(modifier = Modifier.padding(it))
                    StartScreen(modifier = Modifier.padding(it))
                    //NovedadScreen(modifier = Modifier.padding(it))
                    //ProfileScreen(modifier = Modifier.padding(it))
                    //ReviewDetailScreen(modifier = Modifier.padding(it))
                    //RankingsScreen(modifier = Modifier.padding(it))
                    //DiscoverScreen(modifier= Modifier.padding(it))
                    //WriteReviewScreen(modifier = Modifier.padding(it))
                    //SettingsRoute(darkMode = true , {},modifier = Modifier.padding(it))
                   //NotificationScreen(modifier = Modifier.padding(it))
                }
            }
        }
    }
}