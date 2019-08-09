package com.google.samples.apps.sunflower.utilities

import android.content.Context
import com.google.samples.apps.sunflower.data.source.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.source.PlantRepository
import com.google.samples.apps.sunflower.data.source.local.AppDatabase
import com.google.samples.apps.sunflower.mvvm.garden.garden_planting.GardenPlantingListViewModelFactory
import com.google.samples.apps.sunflower.mvvm.garden.plant_detail.PlantDetailViewModelFactory
import com.google.samples.apps.sunflower.mvvm.garden.plant_list.PlantListViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getPlantRepository(context: Context): PlantRepository {
        return PlantRepository.getInstance(
                AppDatabase.getInstance(context.applicationContext).plantDao())
    }

    private fun getGardenPlantingRepository(context: Context): GardenPlantingRepository {
        return GardenPlantingRepository.getInstance(
                AppDatabase.getInstance(context.applicationContext).gardenPlantingDao())
    }

    fun provideGardenPlantingListViewModelFactory(
        context: Context
    ): GardenPlantingListViewModelFactory {
        val repository = getGardenPlantingRepository(context)
        return GardenPlantingListViewModelFactory(repository)
    }

    fun providePlantListViewModelFactory(context: Context): PlantListViewModelFactory {
        val repository = getPlantRepository(context)
        return PlantListViewModelFactory(repository)
    }

    fun providePlantDetailViewModelFactory(
        context: Context,
        plantId: String
    ): PlantDetailViewModelFactory {
        return PlantDetailViewModelFactory(getPlantRepository(context),
                getGardenPlantingRepository(context), plantId)
    }
}
