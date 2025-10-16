package com.example.openconversor

import MonedasViewModel
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.openconversor.data.model.Moneda
import com.example.openconversor.utils.ConversorUtils.convertirMoneda


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var spMonedaAntes : Spinner = findViewById(R.id.spMonedaAntes)
        var spMonedaDespues : Spinner = findViewById(R.id.spMonedaDespues)
        val cantidad : EditText = findViewById(R.id.edCantidad)
        val btnCalcular : Button = findViewById(R.id.btnCalcular)
        var txtResultado : TextView = findViewById(R.id.txtResultado)

        val monedaCLP = Moneda(
            Codigo = "CLP",
            Nombre = "Peso Chileno",
            Valor = "1"  // CLP respecto a CLP es 1
        )

        val viewModel : MonedasViewModel by viewModels()
        var listaMonedas : List<Moneda> = emptyList()

        // Observar la lista de monedas desde el ViewModel
        viewModel.monedas.observe(this) { monedas ->
            // Crear una copia mutable
            val listaConCLP = monedas.toMutableList()
            listaConCLP.add(0, monedaCLP)  // agregamos CLP al inicio

            listaMonedas = listaConCLP

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listaMonedas.map { it.Nombre } // solo nombres para mostrar
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            spMonedaAntes.adapter = adapter
            spMonedaDespues.adapter = adapter
        }

        viewModel.error.observe(this) { mensaje ->
            txtResultado.text = "Error: $mensaje"
            println("Error: $mensaje")
        }

        // Cargar las monedas al iniciar
        viewModel.cargarMonedas()

        btnCalcular.setOnClickListener {
            if (listaMonedas.isEmpty()) {
                txtResultado.text = "Monedas aún no cargadas"
                return@setOnClickListener
            }

            val cantidadTexto = cantidad.text.toString()
            if (cantidadTexto.isEmpty()) {
                txtResultado.text = "Ingresa una cantidad válida"
                return@setOnClickListener
            }

            val cantidadDouble = cantidadTexto.toDoubleOrNull()
            if (cantidadDouble == null) {
                txtResultado.text = "Formato de número incorrecto"
                return@setOnClickListener
            }

            // Obtener las monedas seleccionadas de los spinners
            val monedaOrigen = listaMonedas[spMonedaAntes.selectedItemPosition]
            val monedaDestino = listaMonedas[spMonedaDespues.selectedItemPosition]

            // Calcular la conversión
            val resultado = convertirMoneda(cantidadDouble, monedaOrigen, monedaDestino)

            // Mostrar resultado
            txtResultado.text = String.format(
                "%.2f %s = %.2f %s",
                cantidadDouble,
                monedaOrigen.Codigo,
                resultado,
                monedaDestino.Codigo
            )
        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}