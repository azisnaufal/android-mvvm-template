package com.google.samples.apps.sunflower.mvvm.garden.plant_detail

import android.content.Intent
import android.os.Build
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.databinding.FragmentPlantDetailBinding
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.utilities.base.BaseFragment
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

/**
 * A fragment representing a single Plant detail screen.
 */
class PlantDetailFragment : BaseFragment<PlantDetailViewModel, FragmentPlantDetailBinding>(R.layout.fragment_plant_detail) {
    override fun afterInflateView() {
        mBinding.apply {
            viewModel = plantDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            fab.setOnClickListener { view ->
                hideAppBarFab(fab)
                plantDetailViewModel.addPlantToGarden()
                Snackbar.make(view, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG).show()
            }

            var isToolbarShown = false

            // scroll change listener begins at Y = 0 when image is fully collapsed
            plantDetailScrollview.setOnScrollChangeListener(
                    NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                        // User scrolled past image to height of toolbar and the title text is
                        // underneath the toolbar, so the toolbar should be shown.
                        val shouldShowToolbar = scrollY > toolbar.height

                        // The new state of the toolbar differs from the previous state; update
                        // appbar and toolbar attributes.
                        if (isToolbarShown != shouldShowToolbar) {
                            isToolbarShown = shouldShowToolbar

                            // Use shadow animator to add elevation if toolbar is shown
                            appbar.isActivated = shouldShowToolbar

                            // Show the plant name if toolbar is shown
                            toolbarLayout.isTitleEnabled = shouldShowToolbar
                        }
                    }
            )

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_share -> {
                        createShareIntent()
                        true
                    }
                    else -> false
                }
            }
        }
        mParentVM = plantDetailViewModel
        setHasOptionsMenu(true)
    }

    private val args: PlantDetailFragmentArgs by navArgs()
    private lateinit var shareText: String

    private val plantDetailViewModel: PlantDetailViewModel by viewModels {
        InjectorUtils.providePlantDetailViewModelFactory(requireActivity(), args.plantId)
    }

    override fun onCreateObserver(viewModel: PlantDetailViewModel) {
        viewModel.apply {
            plant.observe(viewLifecycleOwner) { plant ->
                shareText = if (plant == null) {
                    ""
                } else {
                    getString(R.string.share_text_plant, plant.name)
                }
            }
        }
    }

    override fun setContentData() {}

    override fun setMessageType(): String = MESSAGE_TYPE_SNACK_CUSTOM

    // Helper function for calling a share functionality.
    // Should be used when user presses a share button/menu item.
    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        val shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setText(shareText)
                .setType("text/plain")
                .createChooserIntent()
                .apply {
                    // https://android-developers.googleblog.com/2012/02/share-with-intents.html
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // If we're on Lollipop, we can open the intent as a document
                        addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                    } else {
                        // Else, we will use the old CLEAR_WHEN_TASK_RESET flag
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                    }
                }
        startActivity(shareIntent)
    }

    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // We want to turn this behavior off to hide the FAB when it is clicked.
    //
    // This is adapted from Chris Banes' Stack Overflow answer: https://stackoverflow.com/a/41442923
    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }
}
