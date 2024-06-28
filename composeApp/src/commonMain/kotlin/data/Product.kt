package data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("category")
    val category: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("image")
    val image: String? = null,
    @SerialName("price")
    val price: Double? = null,
    @SerialName("rating")
    val rating: Rating? = null,
    @SerialName("title")
    val title: String? = null
)

val errorFromAPI = listOf(
    Product(
        category = "",
        description = "",
        id = 0,
        price = 200.0,
        rating = Rating(count = 5, rate = 4.5),
        title = "Error from API"
    )
)


val errorFromException = listOf(
    Product(
        category = "",
        description = "",
        id = 0,
        price = 200.0,
        rating = Rating(count = 5, rate = 4.5),
        title = "Error from Exception"
    )
)