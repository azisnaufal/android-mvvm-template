package com.google.samples.apps.sunflower.data.source.remote.plants

import com.google.samples.apps.sunflower.data.model.Plant
import retrofit2.http.GET

interface PlantsApiService {

    @GET("plants.json")
    suspend fun getPlants(): List<Plant>

}