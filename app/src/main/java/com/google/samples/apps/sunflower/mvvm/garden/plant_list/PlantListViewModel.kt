package com.google.samples.apps.sunflower.mvvm.garden.plant_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.samples.apps.sunflower.data.model.Plant
import com.google.samples.apps.sunflower.data.source.PlantRepository
import com.google.samples.apps.sunflower.utilities.base.BaseViewModel
import com.google.samples.apps.sunflower.utilities.helper.Event
import kotlinx.coroutines.launch

/**
 * The ViewModel for [PlantListFragment].
 */
class PlantListViewModel internal constructor(val plantRepository: PlantRepository) : BaseViewModel() {

    private val growZoneNumber = MutableLiveData<Int>().apply { value = NO_GROW_ZONE }

    var plants: MutableLiveData<List<Plant>> = MutableLiveData()

    fun setPlants() {
        if (growZoneNumber.value == NO_GROW_ZONE) {
            isRequesting.value = Event(true)
            viewModelScope.launch {
                plants.value = plantRepository.getPlants()
                isRequesting.value = Event(false)
            }
        } else {
            plants.value = plantRepository.getPlantsWithGrowZoneNumber(growZoneNumber.value!!).value
        }
    }

    fun setGrowZoneNumber(num: Int) {
        growZoneNumber.value = num
    }

    fun clearGrowZoneNumber() {
        growZoneNumber.value = NO_GROW_ZONE
    }

    fun isFiltered() = growZoneNumber.value != NO_GROW_ZONE

    companion object {
        private const val NO_GROW_ZONE = -1
    }
}
