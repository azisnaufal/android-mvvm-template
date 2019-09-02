package com.google.samples.apps.sunflower.data.source

import android.util.Log
import com.google.samples.apps.sunflower.data.model.Plant
import com.google.samples.apps.sunflower.data.source.local.PlantDao
import com.google.samples.apps.sunflower.data.source.remote.ApiService

/**
 * Repository module for handling data operations.
 */
class PlantRepository private constructor(private val plantDao: PlantDao) {

    private val TAG by lazy { PlantRepository::class.java.simpleName }

    suspend fun getPlants(): List<Plant> {
        val response = ApiService.plantsApiService.getPlants()
        try {
            if (response.isNotEmpty()) {
                plantDao.insertAll(response)
            }
        } catch (e: Throwable) {
            Log.e(TAG, e.toString())
        }
        return plantDao.getPlants()
    }

    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    suspend fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
            plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PlantRepository? = null

        fun getInstance(plantDao: PlantDao) =
                instance ?: synchronized(this) {
                    instance
                            ?: PlantRepository(plantDao).also { instance = it }
                }
    }
}
