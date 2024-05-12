package com.example.vkpoketestapp.datalayer

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonRepository {

    @GET("pokemon/?offset=0&limit=20")
    suspend fun getResultList(): ListOfResults

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id:Int): Pokemon

    @GET("characteristic/{id}")
    suspend fun getPokemonDescription(@Path("id") id:Int): PokemonDescription


    companion object {
        val pokemonRepository: PokemonRepository = createPokemonRepository()

        private fun createPokemonRepository(): PokemonRepository {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder().apply {
                addNetworkInterceptor(loggingInterceptor)
            }.build()

            val retrofit = Retrofit.Builder().apply {
                client(client)
                addConverterFactory(GsonConverterFactory.create())
                baseUrl("https://pokeapi.co/api/v2/")
            }.build()
            return retrofit.create(PokemonRepository::class.java)
        }
    }
}