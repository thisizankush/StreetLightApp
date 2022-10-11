package com.android.streetlight.services.blockModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class BlockViewModel : ViewModel() {
    fun getBlock(district:String): LiveData<BlockDataModel> = BlockRepo().getBlockData(district)

}