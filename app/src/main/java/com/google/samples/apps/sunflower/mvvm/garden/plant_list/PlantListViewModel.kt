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

    val growZoneNumber = MutableLiveData<Int>(NO_GROW_ZONE)

    var plants = MutableLiveData<List<Plant>>()

    fun setPlants() {
        isRequesting.value = Event(true)
        viewModelScope.launch {
            plants.value = if (growZoneNumber.value == NO_GROW_ZONE) plantRepository.getPlants() else plantRepository.getPlantsWithGrowZoneNumber(growZoneNumber.value!!)
            isRequesting.value = Event(false)
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
