package com.android.streetlight.services.luminaryWithSerialModule

import androidx.lifecycle.ViewModel

class LumnaryWIthSerialViewModel : ViewModel() {
    fun getLuminaryWithSerial(serial_number : String) = LuminaryWIthSerialRepo().getLuminaryWithSerial(serial_number)
}