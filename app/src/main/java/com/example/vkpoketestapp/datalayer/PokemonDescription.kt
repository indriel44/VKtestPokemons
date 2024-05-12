package com.example.vkpoketestapp.datalayer

import com.google.gson.annotations.SerializedName

data class PokemonDescription(

    @SerializedName("descriptions")
    val descriptionsList: List<Description>
) {

    data class Description(
        @SerializedName("description")
        val description: String
    )
}

