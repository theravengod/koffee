package cheshire.kitty.koffee.net

import cheshire.kitty.koffee.models.Product
import cheshire.kitty.koffee.other.DynamicKeySerializer
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CoffeeApiImpl @Inject constructor(
    private val apiClient: HttpClient,
    private val jsonParser: Json
) : CoffeeApi {
    override fun getProducts(page: Int): Flow<List<Product>> {
        return flow {
            val rawResponse = apiClient.get("https://valentinos-coffee.herokuapp.com/products") {
                parameter("page", page)
            }.bodyAsText()

            if (rawResponse.isNotBlank()) {
                emit(jsonParser.decodeFromString(DynamicKeySerializer(Product.serializer(), "products"), rawResponse).items)
            } else {
                emit(emptyList())
            }
        }
    }
}