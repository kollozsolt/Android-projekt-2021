package com.example.projectapplication.api

import com.example.projectapplication.model.*
import com.example.projectapplication.utils.Constants
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(Constants.RESET_PASSWORD_URL)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse

    @POST(Constants.REGISTRATION_URL)
    suspend fun registration(@Body request: RegistrationRequest) : RegistrationResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String?,
                            @Header("limit") limit: Int?,
                            @Header("filter") filter: String?,
                            @Header("sort") sort: String?,
                            @Header("skip") skip: Int?): ProductResponse
//

    @GET(Constants.TOKEN_PASSWORD_RESET_URL)
    suspend fun tokenResetPassword(@Header("token") token: String,
                                    @Header("new_password") new_password: String) : ResetPasswordResponse

    @GET(Constants.USER_INFO)
    suspend fun getUserInfo(@Header("username") username: String?) : UserInfoResponse

    @Multipart
    @POST(Constants.ADD_PRODUCT_URL)
    suspend fun addProduct(@Header("token") token: String,
                            @Part("title") title: String,
                            @Part("description") description: String,
                            @Part("price_per_unit") price_per_unit: String,
                            @Part("units") units: String,
                            @Part("is_active") is_active: Boolean,
                            @Part("amount_type") amount_type: String,
                            @Part("price_type") price_type: String,
                            @Part("rating") rating: Double) : AddProductResponse

    @POST(Constants.DELETE_PRODUCT_URL)
    suspend fun deleteProduct( @Header("token") token: String,
                               @Query("product_id") product_id : String) : DeleteProductResponse

    @Multipart
    @POST(Constants.UPDATE_PROFILE_URL)
    suspend fun updateUserData(@Header("token") token: String,
                                @Part("phone_number") phone_number: Int,
                                @Part("email") email: String,
                                @Part("username") username: String ) : UserUpdateResponse

    @GET(Constants.RESET_PASSWORD_URL)
    suspend fun updatePassword(@Header("token") token: String?,
                                @Header("new_password") new_password: String?) : ResetPasswordResponse

    @GET(Constants.ORDERS_URL)
    suspend fun getOrders(@Header("token") token: String?,
                            @Header("limit") limit: Int?,
                            @Header("filter") filter: String?,
                            @Header("sort") sort: String?): FareResponse

    @Multipart
    @POST(Constants.SEND_ORDER_URL)
    suspend fun sendOrder(@Header("token") token: String,
                          @Part("title") title: String,
                          @Part("description") description: String,
                          @Part("price_per_unit") price_per_unit: String,
                          @Part("units") units: String,
                          @Part("owner_username") owner_username: String) : AddOrderResponse
}