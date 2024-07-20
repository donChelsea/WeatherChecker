package com.example.weatherchecker.di

import com.example.weatherchecker.data.remote.repository.WeatherRepositoryImpl
import com.example.weatherchecker.domain.location.DefaultLocationTracker
import com.example.weatherchecker.domain.location.LocationTracker
import com.example.weatherchecker.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository

}