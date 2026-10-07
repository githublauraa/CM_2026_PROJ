package com.example.voxel_review.data.dataSource

import com.example.voxel_review.data.review.ReviewInfo

interface ReviewRemoteDataSource {
	suspend fun getAllReviews(): List<ReviewInfo>
}
