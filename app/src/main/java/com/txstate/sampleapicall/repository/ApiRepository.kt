package com.txstate.sampleapicall.repository

import com.txstate.sampleapicall.model.ApiCall
import com.txstate.sampleapicall.api.ApiService
import com.txstate.sampleapicall.model.AddProductRequest
import com.txstate.sampleapicall.model.AuthResponse
import com.txstate.sampleapicall.model.LoginRequest
import com.txstate.sampleapicall.model.SignupRequest

class ApiRepository(private  val api : ApiService) {
    // 1. Login
    suspend fun login(request: LoginRequest): ApiResult<AuthResponse> {
        return safeApiCall { api.login(request) }
    }
    // 2. Signup
    suspend fun signup(request: SignupRequest): ApiResult<AuthResponse> {
        return safeApiCall { api.signup(request) }
    }

    suspend fun createProduct(request: AddProductRequest): ApiResult<AuthResponse> {
        return safeApiCall { api.createProduct(request) }
    }

}