@file:OptIn(ExperimentalMaterial3Api::class)

package com.txstate.sampleapicall.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddProductScreen(
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var availableQty by remember { mutableStateOf("") }
    var pricePerUnit by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }

    // Local category data
    val categories = listOf(
        1 to "Fruits",
        2 to "Vegetables",
        3 to "Dairy",
        4 to "Grains",
        5 to "Spices"
    )

    // Dropdown state
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Pair<Int, String>?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Add Product",
            style = MaterialTheme.typography.headlineMedium
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = availableQty,
            onValueChange = { availableQty = it },
            label = { Text("Available Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = pricePerUnit,
            onValueChange = { pricePerUnit = it },
            label = { Text("Price per Unit") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = unit,
            onValueChange = { unit = it },
            label = { Text("Unit (e.g. kg, L)") },
            modifier = Modifier.fillMaxWidth()
        )

        // ✅ Category Dropdown with Persistent Selection
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedCategory?.second ?: "", // Always shows the selected value
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.second) },
                        onClick = {
                            selectedCategory = category // ✅ Persist selected category
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                selectedCategory?.let {
                    val product = Product(
                        product_id = 0,
                        name = name,
                        description = description,
                        available_qty = availableQty.toIntOrNull() ?: 0,
                        price_per_unit = pricePerUnit.toDoubleOrNull() ?: 0.0,
                        unit = unit,
                        category_id = it.first
                    )
                    //onSave(product)
                }
            },
            enabled = selectedCategory != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Product")
        }

        OutlinedButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}


