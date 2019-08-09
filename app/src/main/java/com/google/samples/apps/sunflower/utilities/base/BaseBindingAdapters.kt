package com.google.samples.apps.sunflower.utilities.base

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}
