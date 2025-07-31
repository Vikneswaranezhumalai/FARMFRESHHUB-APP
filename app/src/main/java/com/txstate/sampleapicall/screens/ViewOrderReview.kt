package com.txstate.sampleapicall.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.txstate.sampleapicall.model.Order

@Composable
fun ViewOrderScreen() {
    val orders = listOf(
        Order(1, "Tomato", 2, 4.0, "Delivered"),
        Order(2, "Apple", 5, 15.0, "Pending"),
        Order(3, "Rice", 1, 1.5, "Shipped")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "My Orders",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {

            items(orders) { order ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Order ID: ${order.orderId}", style = MaterialTheme.typography.titleMedium)
                        Text("Product: ${order.productName}")
                        Text("Quantity: ${order.quantity}")
                        Text("Total Price: $${order.totalPrice}")
                        Text("Status: ${order.status}", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
@Composable
fun ViewReviews() {
    val reviews = listOf(
        Review("Tomato", 4.5, "Very fresh and tasty!"),
        Review("Apple", 4.0, "Sweet and juicy apples."),
        Review("Rice", 5.0, "Best quality rice I've purchased!")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "My Reviews",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(reviews) { review ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Product: ${review.productName}", style = MaterialTheme.typography.titleMedium)
                        Text("Rating: ⭐ ${review.rating}")
                        Text("Review: ${review.reviewText}")
                    }
                }
            }
        }
    }
}

// Data Model
data class Review(
    val productName: String,
    val rating: Double,
    val reviewText: String
)
