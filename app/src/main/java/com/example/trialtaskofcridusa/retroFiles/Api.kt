package com.example.trialtaskofcridusa.retroFiles

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface Api {

    @FormUrlEncoded
    @POST("TrialProfile")
    fun subMitData(@FieldMap value: HashMap<String, Any>): Call<JsonObject?>?

    @FormUrlEncoded
    @POST("TrialNotification")
    fun setNotification(@FieldMap value: HashMap<String, Any>): Call<JsonObject?>?

    @POST("TrialNotification")
    fun getNotification(): Call<JsonObject?>?
}