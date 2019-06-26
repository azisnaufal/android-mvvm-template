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


    override fun onDestroy() {
        super.onDestroy()
        onDestroyObserver(mParentVM)
    }


    abstract fun onCreateObserver(viewModel: T)
    abstract fun setContentData()
    abstract fun setMessageType(): String
    abstract fun onDestroyObserver(viewModel: T)


    companion object {
        const val MESSAGE_TYPE_TOAST = "TOAST_TYPE"
        const val MESSAGE_TYPE_SNACK = "SNACK_TYPE"
        const val MESSAGE_TYPE_SNACK_CUSTOM = "SNACK_CUSTOM_TYPE"
    }

}