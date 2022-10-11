package com.android.streetlight.services.assetModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class AssetDataViewModel : ViewModel() {
    fun createAsset(assetData: AssetDataModel) : LiveData<AssetResponseModel> = AssetRepo().createAsset(assetData)
}