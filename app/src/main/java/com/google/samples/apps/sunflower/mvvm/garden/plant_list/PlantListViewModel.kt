package com.google.samples.apps.sunflower.mvvm.garden.plant_list

import androidx.lifecycle.LiveData
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

    private val _growZoneNumber = MutableLiveData<Int>(NO_GROW_ZONE)
    val growZoneNumber: LiveData<Int>
        get() = _growZoneNumber

    private var _plants = MutableLiveData<List<Plant>>()
    val plants: LiveData<List<Plant>>
        get() = _plants

    fun setPlants() {
        isRequesting.value = Event(true)
        viewModelScope.launch {
            _plants.value = if (_growZoneNumber.value == NO_GROW_ZONE) plantRepository.getPlants() else plantRepository.getPlantsWithGrowZoneNumber(_growZoneNumber.value!!)
            isRequesting.value = Event(false)
        }
    }

    fun setGrowZoneNumber(num: Int) {
        _growZoneNumber.value = num
    }

    fun clearGrowZoneNumber() {
        _growZoneNumber.value = NO_GROW_ZONE
    }

    fun isFiltered() = _growZoneNumber.value != NO_GROW_ZONE

    companion object {
        private const val NO_GROW_ZONE = -1
    }
}
