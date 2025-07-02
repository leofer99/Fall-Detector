package org.falldetectives.falldetector.modules.androidlargeone.data.viewmodel;

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antniasapplication.app.modules.androidlargeone.`data`.model.AndroidLargeOneModel
import org.koin.core.KoinComponent

class AndroidLargeOneVM : ViewModel(), KoinComponent {
  val androidLargeOneModel: MutableLiveData<AndroidLargeOneModel> =
      MutableLiveData(AndroidLargeOneModel())

  var navArguments: Bundle? = null
}
