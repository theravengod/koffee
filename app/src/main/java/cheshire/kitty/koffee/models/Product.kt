package cheshire.kitty.koffee.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Product(
    val id: Int,
    val name: String? = null,
    val category: String? = null,
    val isAvailable: Boolean = false
)
