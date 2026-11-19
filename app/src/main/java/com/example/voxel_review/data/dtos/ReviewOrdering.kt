package com.example.voxel_review.data.dtos

import java.time.Instant

/** Publication order; legacy reviews without a valid date stay after dated reviews. */
fun List<ReviewDto>.newestFirst(): List<ReviewDto> = sortedWith(
    compareByDescending<ReviewDto> {
        runCatching { Instant.parse(it.createdAt) }.getOrDefault(Instant.MIN)
    }.thenBy { it.reviewId }
)
