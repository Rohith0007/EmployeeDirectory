package network

import EmployeeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: EmployeeApiService by lazy {
        retrofit.create(EmployeeApiService::class.java)
    }
}

