package com.google.samples.apps.sunflower.mvvm.garden.garden_planting

import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.utilities.base.BaseFragment

class GardenFragment : BaseFragment<GardenPlantingListViewModel, FragmentGardenBinding>(R.layout.fragment_garden) {
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

    override fun afterInflateView() {
        mParentVM = viewModel
    }
}
