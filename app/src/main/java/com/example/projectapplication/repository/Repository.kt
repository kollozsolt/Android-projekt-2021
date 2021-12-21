package com.example.projectapplication.repository


import android.util.Log
import com.example.projectapplication.api.RetrofitInstance
import com.example.projectapplication.model.*

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun resetPassword(request: ResetPasswordRequest): ResetPasswordResponse{
        return RetrofitInstance.api.resetPassword(request)
    }

    suspend fun registration(request: RegistrationRequest) : RegistrationResponse {
        return RetrofitInstance.api.registration(request)
    }

    suspend fun getProducts(token: String?, limit: Int?, filter: String?, sort: String?, skip: Int?) : ProductResponse {
        return RetrofitInstance.api.getProducts(token, limit, filter, sort, skip)
    }

    suspend fun tokenPasswordReset(token: String, new_password: String) : ResetPasswordResponse{
        return RetrofitInstance.api.tokenResetPassword(token, new_password)
    }

    suspend fun getUserInfo(username: String?) : UserInfoResponse{
        return RetrofitInstance.api.getUserInfo(username)
    }

    suspend fun addProduct(token: String, title:String, description:String, price_per_unit: String, units: String, is_activated:Boolean, amount_type: String, price_type:String, rating:Double) :AddProductResponse{
        return RetrofitInstance.api.addProduct(token,title, description, price_per_unit, units, is_activated, amount_type, price_type, rating)
    }

    suspend fun deleteProduct(token: String, product_id: String) : DeleteProductResponse{
        return RetrofitInstance.api.deleteProduct(token, product_id)
    }

    suspend fun updateUserData(token: String, phone_number: Int, email: String, username: String) : UserUpdateResponse{
        return RetrofitInstance.api.updateUserData(token, phone_number, email.replace("\"", ""), username.replace("\"", ""))
    }

    suspend fun updatePassword(token: String?, new_password: String?) :ResetPasswordResponse{
        return RetrofitInstance.api.updatePassword(token, new_password)
    }
}