package cheshire.kitty.koffee.ui.vms

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cheshire.kitty.koffee.net.CoffeeApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val api: CoffeeApi
): ViewModel() {

    private var page = 0

    val products = api.getProducts(page)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

}