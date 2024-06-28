package database.datasource

import data.Product
import data.errorFromAPI
import data.errorFromException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode


class ProductsRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getAllProducts(): List<Product> {
        return try {
            // Making a GET request to the API endpoint
            val response = httpClient.get("https://fakestoreapi.com/products")

            // Check if the response status is successful
            if (response.status == HttpStatusCode.OK) {
                // Parse and return the response body
                response.body()
            } else {
                // Handle non-OK response status
                println("Failed to fetch products: ${response.status}")
                errorFromAPI

            }
        } catch (e: Exception) {
            // Handle exceptions (network errors, JSON parsing errors, etc.)
            println("An error occurred: ${e.message}")
            errorFromException
        }
    }

//
//    suspend fun getAllProducts(): List<Product> {
//        val response = httpClient.get("https://fakestoreapi.com/products")
//            return response.body()
//    }

}