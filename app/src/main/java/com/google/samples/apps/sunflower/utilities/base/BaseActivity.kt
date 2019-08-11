package com.google.samples.apps.sunflower.utilities.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.samples.apps.sunflower.utilities.extensions.*
import com.google.samples.apps.sunflower.utilities.helper.EventObserver

abstract class BaseActivity<T : BaseViewModel, B : ViewDataBinding>(val layoutId: Int) : AppCompatActivity() {

    lateinit var mParentVM: T
    lateinit var mBinding: B
    var mProgressType = PROGRESS_TYPE_BLOCK
    private var mMessageType = MESSAGE_TYPE_SNACK
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutId)
    }

    override fun onStart() {
        super.onStart()
        val parentLayout = findViewById<View>(android.R.id.content)
        mParentVM.apply {
            showMessage.observe(this@BaseActivity, EventObserver {
                if (!it.isEmpty()) {
                    when (mMessageType) {
                        MESSAGE_TYPE_SNACK_CUSTOM -> {
                            parentLayout.showCustomSnackBar(it)
                        }
                        MESSAGE_TYPE_SNACK -> {
                            parentLayout.showSnackBar(it)
                        }
                        else -> {
                            showToast(it)
                        }
                    }
                }
            })

            showMessageRes.observe(this@BaseActivity, EventObserver {
                if (it != 0) {
                    when (mMessageType) {
                        MESSAGE_TYPE_SNACK_CUSTOM -> {
                            parentLayout.showCustomSnackBarRes(it)
                        }
                        MESSAGE_TYPE_SNACK -> {
                            parentLayout.showSnackBarRes(it)
                        }
                        else -> {
                            showToast(it)
                        }
                    }
                }
            })

            isRequesting.observe(this@BaseActivity, EventObserver {
                if (mProgressType.equals(PROGRESS_TYPE_BLOCK)) {
                    if (it == true) {
                        progressDialog.show()
                    } else {
                        progressDialog.dismiss()
                    }
                }
            })
        }
        onCreateObserver(mParentVM)
        setContentData()
        mMessageType = setMessageType()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("")
        progressDialog.setMessage("Mohon tunggu ...")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)

    }

    abstract fun onCreateObserver(viewModel: T)
    abstract fun setContentData()
    abstract fun setMessageType(): String

    companion object {
        const val PROGRESS_TYPE_SWIPE = "PROGRESS_TYPE_SWIPE"
        const val PROGRESS_TYPE_BLOCK = "PROGRESS_TYPE_BLOCK"
        const val MESSAGE_TYPE_TOAST = "TOAST_TYPE"
        const val MESSAGE_TYPE_SNACK = "SNACK_TYPE"
        const val MESSAGE_TYPE_SNACK_CUSTOM = "SNACK_CUSTOM_TYPE"
    }
}