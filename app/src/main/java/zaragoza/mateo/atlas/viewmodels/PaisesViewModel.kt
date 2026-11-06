package zaragoza.mateo.atlas.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zaragoza.mateo.atlas.modelos.Pais
import zaragoza.mateo.atlas.red.RetrofitCliente

class PaisesViewModel : ViewModel() {
    private val _paises = mutableStateOf<List<Pais>>(emptyList())
    val paises: State<List<Pais>> = _paises

    private val _cargando = mutableStateOf(true)
    val cargando: State<Boolean> = _cargando

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    init {
        cargarPaises()
    }

    fun cargarPaises() {
        _cargando.value = true
        _error.value = ""
        viewModelScope.launch {
            try {
                val lista = RetrofitCliente.api.obtenerPaises(CAMPOS)
                _paises.value = lista.sortedBy {it.nombreEs}
            } catch (e: Exception){
                _error.value = "No se pudo cargar la lista. Revisa tu conexión"
            }
            _cargando.value = false
        }
    }
    fun buscarPais(codigo: String): Pais? {
        return _paises.value.find { it.alpha3Code == codigo }
    }
    companion object {
        const val CAMPOS = "name,alpha3Code,translations,flags,capital,region,population,languages,currencies"
    }
}