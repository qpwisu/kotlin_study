package com.hany.retrofittest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
//API 선언 interface
interface Api {
    @GET("v1/search/news.json")
    fun getSearchNews(
        @Header("X-Naver-Client-Id") clientId: String = "3CFjqnPWT4AuEDi09LAe",
        @Header("X-Naver-Client-Secret") clientSecret: String = "jqxWgSrHDW",
        @Query("query") query: String,
        @Query("display") display: Int? = null,
        @Query("start") start: Int? = null
    ): Call<ResultGetSearchNews>
}