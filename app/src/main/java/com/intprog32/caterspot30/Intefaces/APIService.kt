package com.intprog32.caterspot30.Intefaces

import com.intprog32.caterspot30.Data.BookingData
import com.intprog32.caterspot30.Data.CatererData
import com.intprog32.caterspot30.Data.UserData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.PUT

interface ApiService {
    @POST("/users")
    fun registerUser(@Body userData: UserData): Call<UserData>

    @GET("/users/validate")
    fun validateUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<ResponseBody>

    @GET("/users")
    fun getUserByEmail(@Query("email") email: String): Call<UserData>

    @PUT("/users/{id}")
    fun updateUser(
        @Path("id") id: String,
        @Body userData: UserData
    ): Call<UserData>

    @DELETE("/users/{id}")
    fun deleteUser(
        @Path("id") userId: String
    ): Call<ResponseBody>

    @DELETE("/caterers/{id}")
    fun deleteCaterer(@Path("id") id: String): Call<ResponseBody>

    @POST("/caterers")
    fun addCaterer(
        @Body caterer: CatererData
    ): Call<ResponseBody>

    @GET("/caterers")
    fun getCaterers(): Call<List<CatererData>>

    @POST("/bookings")
    fun addBooking(@Body booking: BookingData): Call<Void>
}