package com.example.trialtaskofcridusa.retroFiles

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetroClient() {
    private val baseUrl : String  = "http://sales.criddam.com/api/"
    companion object {
        private lateinit var retro : Retrofit
        private var instant : RetroClient? = null
    }

    private var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()

    var gson = GsonBuilder()
        .setLenient()
        .create()

    init {
        retro = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Synchronized
    fun getInstance(): RetroClient? {
        if (instant == null ) {
            instant = RetroClient()
        }
        return instant
    }

    fun getApi() : Api{
        return retro.create(Api::class.java)
    }
}