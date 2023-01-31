package com.example.myapplication.api

import com.example.myapplication.model.Employee
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface HiringApi {
    @GET(ApiFetcher.HIRING_API)
    fun getEmployeeList(): Observable<List<Employee>>
}

object ApiFetcher {
    const val HIRING_API = "/hiring.json"
    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com";

    private val RETROFIT = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build()
                        .create(HiringApi::class.java)

    fun getEmployeeList(): Observable<List<Employee>> {
        return RETROFIT.getEmployeeList().subscribeOn(Schedulers.io())
    }
}