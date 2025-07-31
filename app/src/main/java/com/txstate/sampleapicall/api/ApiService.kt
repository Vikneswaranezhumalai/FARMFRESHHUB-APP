package com.txstate.sampleapicall.api

import com.txstate.sampleapicall.model.AddProductRequest
import com.txstate.sampleapicall.model.ApiCall
import com.txstate.sampleapicall.model.AuthResponse
import com.txstate.sampleapicall.model.LoginRequest
import com.txstate.sampleapicall.model.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    @POST("users/signup")
    suspend fun signup(@Body request: SignupRequest): Response<AuthResponse>

    @POST("products")
    suspend fun createProduct(@Body request: AddProductRequest) : Response<AuthResponse>
}