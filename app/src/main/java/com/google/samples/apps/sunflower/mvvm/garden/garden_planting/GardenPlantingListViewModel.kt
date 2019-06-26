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