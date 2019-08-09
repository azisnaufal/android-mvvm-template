package com.google.samples.apps.sunflower.mvvm.garden.plant_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.google.samples.apps.sunflower.data.source.PlantRepository

/**
 * Factory for creating a [PlantListViewModel] with a constructor that takes a [PlantRepository].
 */
class PlantListViewModelFactory(
    private val repository: PlantRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = PlantListViewModel(repository) as T
}
