package com.example.vkpoketestapp.datalayer

import com.google.gson.annotations.SerializedName

data class Pokemon(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    private val _name: String?,

    @SerializedName("weight")
    private val _weight: String?,

    @SerializedName("height")
    private val _height: String?,

    @SerializedName("base_experience")
    private val _baseXp: String?,

    @SerializedName("sprites")
    val sprites: Sprites,

    @SerializedName("types")
    val types: List<Types>,

    ) {
    val name: String
        get() = _name ?: "Unknown"

    val weight: String
        get() = _weight ?: "-"

    val height: String
        get() = _height ?: "-"

    val baseXp: String
        get() = _baseXp ?: "-"

    data class Sprites(

        @SerializedName("front_default")
        private val _front: String?,

        @SerializedName("back_default")
        private val _back: String?,
    ) {
        val front: String
            get() = _front ?: ""

        val back: String
            get() = _back ?: ""
    }

    data class Types(

        @SerializedName("type")
        val type: Type,

        ) {
        data class Type(
            @SerializedName("name")
            val type_name: String
        )
    }

}