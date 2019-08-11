package com.google.samples.apps.sunflower.mvvm.garden.plant_list

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.databinding.FragmentPlantListBinding
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.utilities.base.BaseFragment
import com.google.samples.apps.sunflower.utilities.helper.EventObserver

class PlantListFragment : BaseFragment<PlantListViewModel, FragmentPlantListBinding>(R.layout.fragment_plant_list) {
    override fun afterInflateView() {
        if (context != null) {
            setHasOptionsMenu(true)
        }
        mParentVM = viewModel
    }

    private lateinit var binding : FragmentPlantListBinding
    private lateinit var adapter : PlantAdapter
    private val viewModel: PlantListViewModel by viewModels {
        InjectorUtils.providePlantListViewModelFactory(requireContext())
    }

    override fun onCreateObserver(viewModel: PlantListViewModel) {
        viewModel.plants.observe(viewLifecycleOwner) { plants ->
            /**
             *  Plant may return null, but the [observe] extension function assumes it will not be null.
             *  So there will be a warning（Condition `plants != null` is always `true`） here.
             *  I am not sure if the database return data type should be defined as nullable, Such as `LiveData<List<Plant>?>` .
             */
            if (plants != null) adapter.submitList(plants)
        }
        viewModel.isRequesting.observe(viewLifecycleOwner, EventObserver {
            binding.swipeRefresh.isRefreshing = it
        })
    }

    override fun setContentData() {
        adapter = PlantAdapter()
        binding.plantList.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.setPlants()
        }
        viewModel.setPlants()
    }

    override fun setMessageType(): String = MESSAGE_TYPE_SNACK_CUSTOM

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_zone -> {
                updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                clearGrowZoneNumber()
            } else {
                setGrowZoneNumber(9)
            }
        }
    }
}