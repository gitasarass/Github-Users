package com.example.github.api

import com.example.github.data.UserResponse
import com.example.github.data.DetailUserResponse
import com.example.github.ui.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ) : Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ) : Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<User>>


}