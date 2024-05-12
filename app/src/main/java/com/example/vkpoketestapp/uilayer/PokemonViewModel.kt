package com.example.vkpoketestapp.uilayer

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.vkpoketestapp.datalayer.Pokemon
import com.example.vkpoketestapp.domain.InternetConncetionChecker
import com.example.vkpoketestapp.domain.PokemonProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext

class PokemonViewModel : ViewModel() {

    private val pokemonProvider = PokemonProvider()
    private val IOdispatcher: CoroutineDispatcher = Dispatchers.IO
    var currentPokemonId = 1

    val pokemonList: Flow<List<Pokemon>> = channelFlow {
        withContext(IOdispatcher) {
            send(pokemonProvider.getPokemons())
        }
    }

    val pokemon: Flow<Pokemon> = channelFlow {
        withContext(IOdispatcher) {
            send(pokemonProvider.getPokemon(currentPokemonId))
        }
    }

    val pokemonDescription: Flow<Array<Any>> = channelFlow {
        withContext(IOdispatcher) {
            send(pokemonProvider.getPokemonDescription(currentPokemonId))
        }
    }

    fun internetConnected(context: Context?): Boolean =
        InternetConncetionChecker.isInternetAvailable(context)
}