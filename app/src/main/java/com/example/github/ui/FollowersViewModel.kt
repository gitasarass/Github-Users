package com.example.github.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {

    val listFollowers = MutableLiveData<ArrayList<User>>()
    val isLoading = MutableLiveData<Boolean>()

    fun setListFollowers(username: String){
        isLoading.postValue(true)

        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        listFollowers.postValue(response.body())
                    }
                    isLoading.postValue(false)
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                    isLoading.postValue(false)
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<User>>{
        return listFollowers
    }

    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }
}