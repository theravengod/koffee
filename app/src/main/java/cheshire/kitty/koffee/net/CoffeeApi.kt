package cheshire.kitty.koffee.net

import cheshire.kitty.koffee.models.Product
import kotlinx.coroutines.flow.Flow

interface CoffeeApi {
    fun getProducts(page: Int = 0): Flow<List<Product>>
}