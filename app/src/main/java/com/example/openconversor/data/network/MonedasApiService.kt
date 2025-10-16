package com.example.openconversor.data.network
import com.example.openconversor.data.model.Moneda
import retrofit2.Call
import retrofit2.http.GET

interface MonedasApiService {
    @GET("general/public/monedas")
    suspend fun getMonedas(): List<Moneda>
}