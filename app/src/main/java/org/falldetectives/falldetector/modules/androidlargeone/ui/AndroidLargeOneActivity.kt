package com.antniasapplication.app.modules.androidlargeone.ui

import androidx.activity.viewModels
import com.antniasapplication.app.R
import com.antniasapplication.app.appcomponents.base.BaseActivity
import com.antniasapplication.app.databinding.ActivityAndroidLargeOneBinding
import com.antniasapplication.app.modules.androidlargeone.`data`.viewmodel.AndroidLargeOneVM
import kotlin.String
import kotlin.Unit

class AndroidLargeOneActivity :
    BaseActivity<ActivityAndroidLargeOneBinding>(R.layout.activity_android_large_one) {
  private val viewModel: AndroidLargeOneVM by viewModels<AndroidLargeOneVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.androidLargeOneVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "ANDROID_LARGE_ONE_ACTIVITY"

  }
}
