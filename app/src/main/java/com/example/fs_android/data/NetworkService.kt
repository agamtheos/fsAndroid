package com.example.fs_android.data

import com.example.fs_android.data.request.LoginRequest
import com.example.fs_android.data.response.BaseResponse
import com.example.fs_android.data.response.batch.BatchResponse
import com.example.fs_android.data.response.login.LoginResponse
import com.example.fs_android.data.response.pagination.PaginationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @POST("api/auth/v1/login")
    suspend fun postLogin(
        @Header("is_mock") isMock: Boolean = true,
        @Header("is_guest") isGuest: Boolean = true,
        @Body loginRequest: LoginRequest
    ): Response<BaseResponse<LoginResponse>>

    // create annotiation for get using path variable and query
    @GET("api/v1/funder-statement-batch/{page}/{sort}/{sort-by}")
    suspend fun getListBatch(
        @Path("page") page: Int,
        @Path("sort") sort: String,
        @Path("sort-by") sortBy: String,
        @Query("size") size: Int? = null,
        @Query("filter-by") filterBy: String? = null,
        @Query("value") filterValue: String? = null
    ): Response<BaseResponse<PaginationResponse>>

    @GET("api/v1/funder-statement-batch/{id}")
    suspend fun getBatch(
        @Path("id") id: String
    ): Response<BaseResponse<BatchResponse>>

}