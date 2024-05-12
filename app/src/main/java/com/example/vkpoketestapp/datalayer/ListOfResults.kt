package com.example.vkpoketestapp.datalayer

import com.google.gson.annotations.SerializedName

data class ListOfResults(

    @SerializedName("results")
    private val _results: List<Result>?,
) {
    val results: List<Result>
        get() = _results ?: emptyList()

    data class Result(
        @SerializedName("name")
        val name: String,

        @SerializedName("url")
        val url: String,
    )
}
