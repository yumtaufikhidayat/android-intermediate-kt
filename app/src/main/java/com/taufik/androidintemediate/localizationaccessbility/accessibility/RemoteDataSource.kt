package com.taufik.androidintemediate.localizationaccessbility.accessibility

import android.content.Context
import com.taufik.androidintemediate.R

class RemoteDataSource(private val context: Context) {
    fun getDetailProduct(): ProductModel {
        return ProductModel(
            name = context.getString(R.string.product_name),
            store = context.getString(R.string.product_store),
            size = context.getString(R.string.product_size),
            color = context.getString(R.string.product_color),
            desc = context.getString(R.string.product_description),
            image = R.drawable.shoes,
            date = context.getString(R.string.date),
            rating = context.getString(R.string.rating),
            price = context.getString(R.string.price),
            countRating = context.getString(R.string.count_rating)
        )
    }
}