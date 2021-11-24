package com.hany.papagoapi

import retrofit2.Call
import retrofit2.http.*

//API 선언 interface
interface Api {
    @FormUrlEncoded
    @POST("v1/papago/n2mt")
    fun transferPapago(
        @Header("X-Naver-Client-Id") clientId: String ,
        @Header("X-Naver-Client-Secret") clientSecret: String ,
        @Field("source") source: String,
        @Field("target") target: String,
        @Field("text") text: String
    ): Call<ResultTransferPapago>
}