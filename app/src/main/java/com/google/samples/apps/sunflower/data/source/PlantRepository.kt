/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.data.source

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.google.samples.apps.sunflower.data.model.Plant
import com.google.samples.apps.sunflower.data.source.local.AppDatabase
import com.google.samples.apps.sunflower.data.source.local.PlantDao
import com.google.samples.apps.sunflower.data.source.remote.ApiService
import com.google.samples.apps.sunflower.data.source.remote.plants.PlantsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import kotlin.coroutines.coroutineContext

/**
 * Repository module for handling data operations.
 */
class PlantRepository private constructor(private val plantDao: PlantDao) {

    private val TAG by lazy { PlantRepository::class.java.simpleName }

    suspend fun getPlants() : List<Plant> {
        val response = ApiService.plantsApiService.getPlants().await()
        try {
            if (response.size > 0){
                plantDao.insertAll(response)
            }
        } catch(e : Throwable){
            Log.e(TAG, e.toString())
        }
        return response
    }

    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
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
