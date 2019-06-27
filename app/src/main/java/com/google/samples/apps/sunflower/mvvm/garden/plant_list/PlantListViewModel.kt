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

package com.google.samples.apps.sunflower.mvvm.garden.plant_list

import android.util.Log
import androidx.lifecycle.*
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

    fun setPlants(){
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
