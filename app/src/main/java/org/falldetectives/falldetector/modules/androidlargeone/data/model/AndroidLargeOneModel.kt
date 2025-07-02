package com.antniasapplication.app.modules.androidlargeone.`data`.model

import com.antniasapplication.app.R
import com.antniasapplication.app.appcomponents.di.MyApp
import kotlin.String

data class AndroidLargeOneModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtHiRohan: String? = MyApp.getInstance().resources.getString(R.string.lbl_hi_rohan)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtEmergencyConta: String? =
      MyApp.getInstance().resources.getString(R.string.msg_emergency_conta)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLabel: String? = MyApp.getInstance().resources.getString(R.string.msg_have_you_fallen)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLabelOne: String? = MyApp.getInstance().resources.getString(R.string.msg_connect_with_vi)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLabelTwo: String? = MyApp.getInstance().resources.getString(R.string.msg_check_past_fall)

)
