package com.taufik.androidintemediate.advancedtesting.data.remote.response

import com.google.gson.annotations.SerializedName


data class Source(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Any
)