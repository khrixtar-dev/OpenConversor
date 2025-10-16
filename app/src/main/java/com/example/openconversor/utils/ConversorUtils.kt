package com.example.openconversor.utils

import com.example.openconversor.data.model.Moneda

object ConversorUtils {
    fun convertirMoneda(cantidad: Double, monedaOrigen: Moneda, monedaDestino: Moneda): Double {
        // Convierte los valores a Double, reemplazando la coma por punto
        val valorOrigen = monedaOrigen.Valor.replace(",", ".").toDoubleOrNull() ?: 1.0
        val valorDestino = monedaDestino.Valor.replace(",", ".").toDoubleOrNull() ?: 1.0

        // Primero convertimos la cantidad a CLP
        val enPesosChilenos = cantidad * valorOrigen

        // Luego de CLP a la moneda destino
        return enPesosChilenos / valorDestino
    }
}