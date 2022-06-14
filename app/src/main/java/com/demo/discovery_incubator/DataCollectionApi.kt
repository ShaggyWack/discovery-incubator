package com.demo.discovery_incubator

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface DataCollectionApi {

    @GET("api/issues")
    fun getData(): Observable<List<MovieApiResponseModel>>

    @GET("api/issues/{id}")
    fun getSingleIssueData(@Path("id") id: Int?): Observable<MovieApiResponseModel>
}