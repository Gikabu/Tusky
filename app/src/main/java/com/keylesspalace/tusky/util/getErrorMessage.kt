package com.keylesspalace.tusky.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private fun getErrorMessage(report: com.keylesspalace.tusky.util.PagingRequestHelper.StatusReport): String {
    return com.keylesspalace.tusky.util.PagingRequestHelper.RequestType.values().mapNotNull {
        report.getErrorFor(it)?.message
    }.first()
}

fun com.keylesspalace.tusky.util.PagingRequestHelper.createStatusLiveData(): LiveData<NetworkState> {
    val liveData = MutableLiveData<NetworkState>()
    addListener { report ->
        when {
            report.hasRunning() -> liveData.postValue(NetworkState.LOADING)
            report.hasError() -> liveData.postValue(
                    NetworkState.error(getErrorMessage(report)))
            else -> liveData.postValue(NetworkState.LOADED)
        }
    }
    return liveData
}