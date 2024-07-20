package com.example.weatherchecker.di

import com.example.weatherchecker.common.BASE_URL
import com.example.weatherchecker.data.remote.WeatherApi
import com.example.weatherchecker.data.remote.repository.WeatherRepositoryImpl
import com.example.weatherchecker.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository = WeatherRepositoryImpl(api)

}