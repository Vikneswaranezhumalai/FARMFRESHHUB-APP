package com.txstate.sampleapicall

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.txstate.sampleapicall.api.ApiClient
import com.txstate.sampleapicall.api.ApiService
import com.txstate.sampleapicall.model.ApiCall
import com.txstate.sampleapicall.model.AuthResponse
import com.txstate.sampleapicall.model.LoginRequest
import com.txstate.sampleapicall.model.SignupRequest
import com.txstate.sampleapicall.model.User
import com.txstate.sampleapicall.repository.ApiRepository
import com.txstate.sampleapicall.repository.ApiResult
import com.txstate.sampleapicall.repository.safeApiCall
import com.txstate.sampleapicall.utils.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val reposiotry : ApiRepository = ApiRepository(ApiClient.apiService)
    private val _postlist = MutableStateFlow<List<ApiCall>>(emptyList())
    val post : StateFlow<List<ApiCall>> = _postlist

    private val _loginResult = MutableStateFlow<ApiResult<AuthResponse>>(ApiResult.Loading)
    val loginResult : StateFlow<ApiResult<AuthResponse>> = _loginResult

    private val _signUpResult = MutableStateFlow<ApiResult<AuthResponse>>(ApiResult.Loading)
    val signUpResult : StateFlow<ApiResult<AuthResponse>> = _signUpResult
    private val context = getApplication<Application>().applicationContext

    val userIdFlow = UserPreferences.getUserId(context)
    val userRole = UserPreferences.getUserRole(context)
    val username = UserPreferences.getUserName(context)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = ApiResult.Loading
            Log.e("MainViewModel", "login: " + email +  password )
            val result = reposiotry.login(LoginRequest(email, password))
            Log.e("MainViewModel", "login: " + result )
            _loginResult.value = result
        }
    }

    fun signup(singupRequest: SignupRequest){
        viewModelScope.launch {
            _signUpResult.value = ApiResult.Loading
            val result = reposiotry.signup(singupRequest)
            _signUpResult.value = result

        }
    }

    fun saveUserInformation(user : User, context: Context){
        viewModelScope.launch {
            UserPreferences.saveUser(context, user)
        }
    }
}