import data.Product
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    homeRepository : HomeRepository
) : ViewModel() {

    private val _product = MutableStateFlow<List<Product>>(listOf())
    val product = _product.asStateFlow()

    init {
        viewModelScope.launch {
            homeRepository.getProducts().collect{ product ->
                _product.update { it + product }

            }
        }
    }
}