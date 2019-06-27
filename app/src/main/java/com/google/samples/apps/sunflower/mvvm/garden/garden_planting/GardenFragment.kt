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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.utilities.base.BaseFragment

class GardenFragment : BaseFragment<GardenPlantingListViewModel>() {

    private val viewModel: GardenPlantingListViewModel by viewModels {
        InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext())
    }
    private lateinit var binding : FragmentGardenBinding
    private lateinit var adapter : GardenPlantingAdapter

    override fun onCreateObserver(viewModel: GardenPlantingListViewModel) {
        viewModel.apply {
            gardenPlantings.observe(viewLifecycleOwner) { plantings ->
                binding.hasPlantings = !plantings.isNullOrEmpty()
            }

            plantAndGardenPlantings.observe(viewLifecycleOwner) { result ->
                if (!result.isNullOrEmpty())
                    adapter.submitList(result)
            }
        }
    }

    override fun setContentData() {
        adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter
    }

    override fun setMessageType(): String = MESSAGE_TYPE_SNACK_CUSTOM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGardenBinding.inflate(inflater, container, false)
        mParentVM = viewModel
        return binding.root
    }

}
