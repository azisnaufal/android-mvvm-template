package com.google.samples.apps.sunflower.mvvm.garden.garden_planting

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.google.samples.apps.sunflower.data.source.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.source.local.PlantAndGardenPlantings
import com.google.samples.apps.sunflower.utilities.base.BaseViewModel

class GardenPlantingListViewModel internal constructor(
    gardenPlantingRepository: GardenPlantingRepository
) : BaseViewModel() {

    val gardenPlantings = gardenPlantingRepository.getGardenPlantings()

    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
            gardenPlantingRepository.getPlantAndGardenPlantings().map { plantings ->
                plantings.filter { it.gardenPlantings.isNotEmpty() }
            }
}