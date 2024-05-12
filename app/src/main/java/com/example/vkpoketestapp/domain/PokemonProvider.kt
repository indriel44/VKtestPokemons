package com.example.vkpoketestapp.domain

import com.example.vkpoketestapp.datalayer.Pokemon
import com.example.vkpoketestapp.datalayer.PokemonRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

const val NUMBER_OF_DROPPED_CHARACTERS_FOR_POKEMON_ID = 34

class PokemonProvider() {

    private val pokemonRepository = PokemonRepository.pokemonRepository

    suspend fun getPokemonsIDs(): List<Int> {
        val listID = emptyList<Int>().toMutableList()
        return coroutineScope {
            val bufferList = async { pokemonRepository.getResultList().results }
            bufferList.await().forEach {
                listID.add(
                    it.url.drop(NUMBER_OF_DROPPED_CHARACTERS_FOR_POKEMON_ID).dropLast(1).toInt()
                )
            }
            listID
        }
    }

    suspend fun getPokemon(id: Int) = coroutineScope {
        val pokemon = async { pokemonRepository.getPokemon(id) }
        pokemon.await()
    }

    suspend fun getPokemonDescription(id: Int) = coroutineScope {
        val pokemonDescription = async { pokemonRepository.getPokemonDescription(id) }
        val pokemon = async { pokemonRepository.getPokemon(id) }
        arrayOf(pokemon.await(), pokemonDescription.await().descriptionsList[7].description)
    }

    suspend fun getPokemons(): List<Pokemon> {
        val listID = getPokemonsIDs()
        val listPokemons = emptyList<Pokemon>().toMutableList()
        listID.forEach { id ->
            listPokemons.add(getPokemon(id))
        }
        return listPokemons
    }


}