package com.beantherepodthat.retrofit

import com.beantherepodthat.model.Coffee
import com.beantherepodthat.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("/api/login")
    fun loginUser(
            @Field("email") email: String,
            @Field("password") password: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/api/register")
    fun registerUser(
            @Field("email") email: String,
            @Field("firstname") firstname: String,
            @Field("lastname") lastname: String,
            @Field("password") password: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/api/myCoffees")
    fun registerMyCoffee(
            @Field("email") email: String,
            @Field("id") id: Int,
            @Field("coffeeName") coffeeName: String,
            @Field("coffeeImg") coffeeImg : String,
            @Field("brand") brand: String,
            @Field("description") description: String,
            @Field("intensity") intensity: Int,
            @Field("cupSize") cupSize: String,
            @Field("roast") roast: Int,
            @Field("acidity") acidity: Int,
            @Field("bitterness") bitterness: Int,
            @Field("body") body: Int,
            @Field("ingredients") ingredients: String,
            @Field("machine") machine: String,
            @Field("rate") rate: Float,
            @Field("favorite") favorite: Boolean
    ): Call<ResponseBody>


    @FormUrlEncoded
    @POST("/api/getuserdata")
    suspend fun getUserData(
            @Field("email") email: String
    ): Response<User>

    // @FormUrlEncoded
    @POST("/api/getcoffeelist")
    suspend fun getCoffeeList(
    ): Response<List<Coffee>>
}
