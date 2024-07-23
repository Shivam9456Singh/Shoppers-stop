package com.martinestudio.shoppersstop


import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("products?limit=50")
    fun getProductData(): Call<MyData>
}