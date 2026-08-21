package com.example.voxel_review

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.screens.home.HomeScreen
import com.example.voxel_review.ui.screens.novedades.NovedadScreen
import com.example.voxel_review.ui.screens.profile.ProfileScreen
import com.example.voxel_review.ui.screens.review.ReviewDetailScreen
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme
import androidx.compose.ui.unit.dp
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Voxel_ReviewTheme() {
                Scaffold(

                ){
                     //HomeScreen(modifier = Modifier.padding(it))
                     //NovedadScreen(modifier = Modifier.padding(it))
                    ProfileScreen(modifier = Modifier.padding(it))
                    //ReviewDetailScreen(modifier = Modifier.padding(it))
                }
            }

        }
    }
}
