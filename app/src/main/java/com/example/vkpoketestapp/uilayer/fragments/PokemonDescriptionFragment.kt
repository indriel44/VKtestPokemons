package com.example.vkpoketestapp.uilayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import coil.load
import com.example.vkpoketestapp.R
import com.example.vkpoketestapp.datalayer.Pokemon
import com.example.vkpoketestapp.uilayer.PokemonViewModel
import kotlinx.coroutines.launch

class PokemonDescriptionFragment : Fragment() {

    private var pokemonId: Int = 0
    private val pokemonViewModel = PokemonViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_pokemon_description, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        pokemonId = arguments?.getInt(POKEMON_ID) ?: 0
        pokemonViewModel.currentPokemonId = pokemonId
        setupDescription(view)

        view.findViewById<ImageButton>(R.id.exitButton).setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    private fun setupDescription(view: View) {

        val errorTextView = view.findViewById<TextView>(R.id.textView__pokemoncard_error_text)
        errorTextView.visibility = View.GONE
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar__pokemondescripion)
        progressBar.visibility = View.VISIBLE
        val textViewTypesTxt = view.findViewById<TextView>(R.id.textview__pokemoncard_types_txt)
        val textViewStatsTxt = view.findViewById<TextView>(R.id.textview__pokemoncard_stats_txt)
        textViewTypesTxt.visibility = View.GONE
        textViewStatsTxt.visibility = View.GONE

        val imageViewPokemonFront =
            view.findViewById<ImageView>(R.id.imageView__pokemondescription_front)
        val imageViewPokemonBack =
            view.findViewById<ImageView>(R.id.imageView__pokemondescription_back)
        val textViewPokemonName = view.findViewById<TextView>(R.id.textview__pokemoncard_name)
        val textViewPokemonDescription =
            view.findViewById<TextView>(R.id.textview__pokemoncard_description)
        val textViewPokemonTypes = view.findViewById<TextView>(R.id.textview__pokemoncard_types)
        val textViewPokemonStats = view.findViewById<TextView>(R.id.textview__pokemoncard_stats)
        if (pokemonViewModel.internetConnected(context)) {
            try {
                pokemonViewModel.viewModelScope.launch {
                    pokemonViewModel.pokemonDescription.collect { pokemonDescription ->
                        progressBar.visibility = View.GONE
                        textViewTypesTxt.visibility = View.VISIBLE
                        textViewStatsTxt.visibility = View.VISIBLE
                        val pokemon = pokemonDescription[0] as Pokemon
                        imageViewPokemonFront.load(pokemon.sprites.front)
                        imageViewPokemonBack.load(pokemon.sprites.back)
                        textViewPokemonName.text = pokemon.name.replaceFirstChar {
                            it.uppercaseChar()
                        }
                        var bufferString = ""
                        pokemon.types.forEach {
                            bufferString += "○ ${it.type.type_name}\n"
                        }
                        textViewPokemonTypes.text = bufferString
                        bufferString =
                            "Height: ${pokemon.height} decimetres;\nWeight: ${pokemon.weight} hectograms;\nBase exp: ${pokemon.baseXp}"
                        textViewPokemonStats.text = bufferString
                        textViewPokemonDescription.text = pokemonDescription[1].toString()
                    }
                }
            } catch (error: Throwable) {
                progressBar.visibility = View.GONE
                errorTextView.visibility = View.VISIBLE
                errorTextView.text = error.printStackTrace().toString()
            }
        } else {

            val toast =
                Toast.makeText(context, "Проверьте подключение к интернету", Toast.LENGTH_LONG)
            toast.show()
        }
    }

    companion object {

        private const val POKEMON_ID = "POKEMON_ID_KEY"

        fun newInstance(
            pokemonId: Int
        ) = PokemonDescriptionFragment().apply {
            arguments = Bundle().apply {
                putInt(POKEMON_ID, pokemonId)
            }
        }
    }
}