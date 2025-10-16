import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openconversor.data.model.Moneda
import com.example.openconversor.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class MonedasViewModel : ViewModel() {

    private val _monedas = MutableLiveData<List<Moneda>>()
    val monedas: LiveData<List<Moneda>> = _monedas

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun cargarMonedas() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMonedas()
                _monedas.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
