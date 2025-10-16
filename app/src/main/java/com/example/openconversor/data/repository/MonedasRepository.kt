package com.example.openconversor.data.repository

import com.example.openconversor.data.model.Moneda
import com.example.openconversor.data.network.RetrofitInstance

class MonedasRepository {
    private var cache: List<Moneda>?= null
    private var lastFetchTime: Long = 0

    suspend fun getMonedas(): List<Moneda> {
        val now = System.currentTimeMillis()
        if (cache != null && (now - lastFetchTime) < 10_000){ // 10 segundos
            return cache!!
        }
        val monedas = RetrofitInstance.api.getMonedas()
        cache = monedas
        lastFetchTime = now
        return monedas
    }
}