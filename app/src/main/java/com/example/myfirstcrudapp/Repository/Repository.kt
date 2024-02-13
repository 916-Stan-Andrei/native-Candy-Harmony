package com.example.myfirstcrudapp.Repository

class Repository<T> {
    private val items = mutableListOf<T>()

    // Add a new item to the repository
    fun addItem(item: T) {
        items.add(item)
    }

    // Get all items in the repository
    fun getAllItems(): List<T> {
        return items.toList()
    }

    // Get an item by a given key or property
    fun getItemBy(keySelector: (T) -> Boolean): T? {
        return items.find(keySelector)
    }

    // Update an item in the repository
    fun updateItem(item: T, keySelector: (T) -> Boolean) {
        val index = items.indexOfFirst(keySelector)
        if (index != -1) {
            items[index] = item
        }
    }

    // Remove an item from the repository
    fun removeItem(keySelector: (T) -> Boolean) {
        val itemToRemove = items.find(keySelector)
        if (itemToRemove != null) {
            items.remove(itemToRemove)
        }
    }
}
