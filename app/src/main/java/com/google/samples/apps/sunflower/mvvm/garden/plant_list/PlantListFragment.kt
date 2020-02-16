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
    private lateinit var adapter : PlantAdapter
    private val viewModel: PlantListViewModel by viewModels {
        InjectorUtils.providePlantListViewModelFactory(requireContext())
    }

    override fun onCreateObserver(viewModel: PlantListViewModel) {
        viewModel.growZoneNumber.observe(viewLifecycleOwner) {
            viewModel.setPlants()
        }

        viewModel.plants.observe(viewLifecycleOwner) { plants ->
            adapter.submitList(plants)
        }

        viewModel.isRequesting.observe(viewLifecycleOwner, EventObserver {
            mBinding.swipeRefresh.isRefreshing = it
        })
    }

    override fun afterInflateView() {
        if (context != null) {
            setHasOptionsMenu(true)
        }
        mParentVM = viewModel
    }

    override fun setContentData() {
        adapter = PlantAdapter()
        mBinding.plantList.adapter = adapter
        mBinding.swipeRefresh.setOnRefreshListener {
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