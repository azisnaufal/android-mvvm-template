package com.google.samples.apps.sunflower.mvvm.garden.plant_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.samples.apps.sunflower.data.model.Plant
import com.google.samples.apps.sunflower.data.source.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.source.PlantRepository
import com.google.samples.apps.sunflower.utilities.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * The ViewModel used in [PlantDetailFragment].
 */
class PlantDetailViewModel(
        plantRepository: PlantRepository,
        private val gardenPlantingRepository: GardenPlantingRepository,
        private val plantId: String
) : BaseViewModel() {

    val isPlanted: LiveData<Boolean>
    val plant: LiveData<Plant>

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {

        /* The getGardenPlantingForPlant method returns a LiveData from querying the database. The
         * method can return null in two cases: when the database query is running and if no records
         * are found. In these cases isPlanted is false. If a record is found then isPlanted is
         * true. */
        val gardenPlantingForPlant = gardenPlantingRepository.getGardenPlantingForPlant(plantId)
        isPlanted = gardenPlantingForPlant.map { it != null }

        plant = plantRepository.getPlant(plantId)
    }

    fun addPlantToGarden() {
        viewModelScope.launch {
            gardenPlantingRepository.createGardenPlanting(plantId)
        }
    }
}
