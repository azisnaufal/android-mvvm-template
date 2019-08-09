package com.google.samples.apps.sunflower.utilities.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.samples.apps.sunflower.utilities.extensions.*
import com.google.samples.apps.sunflower.utilities.helper.EventObserver

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    lateinit var mParentVM: T
    private var mMessageType = MESSAGE_TYPE_SNACK

    override fun onViewCreated(paramView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(paramView, savedInstanceState)
        mParentVM.apply {
            showMessage.observe(this@BaseFragment, EventObserver {
                if (!it.isEmpty()) {
                    when (mMessageType) {
                        MESSAGE_TYPE_SNACK_CUSTOM -> {
                            view?.showCustomSnackBar(it)
                        }
                        MESSAGE_TYPE_SNACK -> {
                            view?.showSnackBar(it)
                        }
                        else -> {
                            requireContext().showToast(it)
                        }
                    }
                }
            })

            showMessageRes.observe(this@BaseFragment, EventObserver {
                if (it != 0) {
                    when (mMessageType) {
                        MESSAGE_TYPE_SNACK_CUSTOM -> {
                            view?.showCustomSnackBarRes(it)
                        }
                        MESSAGE_TYPE_SNACK -> {
                            view?.showSnackBarRes(it)
                        }
                        else -> {
                            requireContext().showToast(it)
                        }
                    }
                }
            })
        }
        onCreateObserver(mParentVM)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setContentData()
        mMessageType = setMessageType()
    }


    abstract fun onCreateObserver(viewModel: T)
    abstract fun setContentData()
    abstract fun setMessageType(): String

    companion object {
        const val MESSAGE_TYPE_TOAST = "TOAST_TYPE"
        const val MESSAGE_TYPE_SNACK = "SNACK_TYPE"
        const val MESSAGE_TYPE_SNACK_CUSTOM = "SNACK_CUSTOM_TYPE"
    }

}