package com.example.weatherchecker.data.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}