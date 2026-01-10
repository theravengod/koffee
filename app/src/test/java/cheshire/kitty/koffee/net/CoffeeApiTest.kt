package cheshire.kitty.koffee.net

import cheshire.kitty.koffee.models.Product
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class CoffeeApiTest {

    @Test
    fun `getProducts returns a list of products`() = runTest {
        val mockJson = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
        val mockEngine = MockEngine {
            respond(
                content = """
                    {
                      "totalResults": 33,
                      "currentPage": 1,
                      "totalPages": 4,
                      "products": [
                            {
                                "id": 1,
                                "name": "Espresso",
                                "category": "coffee"
                            },
                            {
                                "id": 2,
                                "name": "Latte",
                                "category": "milk coffee"
                            }
                        ]
                    }
                """,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(mockJson)
            }
        }
        val coffeeApi = CoffeeApiImpl(httpClient, mockJson)

        val products = coffeeApi.getProducts().first()

        val expectedProducts = listOf(
            Product(1, "Espresso", "coffee"),
            Product(2, "Latte", "milk coffee")
        )
        assertEquals(expectedProducts, products)
    }
}
