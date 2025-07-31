package com.txstate.sampleapicall.repository

import retrofit2.Response

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            ApiResult.Success(response.body()!!)
        } else {
            ApiResult.Error(response.message())
        }
    } catch (e: Exception) {
        ApiResult.Error(e.localizedMessage ?: "Unknown error")
    }
}
