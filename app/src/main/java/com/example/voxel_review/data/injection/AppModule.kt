package com.example.voxel_review.data.injection

import com.example.voxel_review.data.dataSource.services.ReviewRetrofitService
import com.example.voxel_review.data.dataSource.services.UserRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Singleton
	@Provides
	fun providesRetrofit(): Retrofit {
		return Retrofit.Builder()
		.baseUrl("http://10.0.2.2:3000/")
		.addConverterFactory(GsonConverterFactory.create())
		.addConverterFactory(ScalarsConverterFactory.create())
		.build()
	}

	@Singleton
	@Provides
	fun providesRetrofitReviewServie(retrofit: Retrofit): ReviewRetrofitService {
		return retrofit.create(ReviewRetrofitService::class.java)
	}

	@Singleton
	@Provides
	fun providesUserRetrofitService(retrofit: Retrofit): UserRetrofitService {
		return retrofit.create(UserRetrofitService::class.java)
	}
}
