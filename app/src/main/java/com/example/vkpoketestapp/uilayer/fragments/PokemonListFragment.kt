package com.example.vkpoketestapp.uilayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vkpoketestapp.R
import com.example.vkpoketestapp.uilayer.PokemonViewModel
import com.example.vkpoketestapp.uilayer.PokemonsListAdapter
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment() {

    private val onLaunchPokemonDescriptionFragment: (pokemonId: Int) -> Unit = { pokemonId ->
        parentFragmentManager
            .beginTransaction()
            .add(R.id.FrameLayout__FragmentHost, PokemonDescriptionFragment.newInstance(pokemonId))
            .addToBackStack("PokemonDescriptionFragment")
            .commit()
    }

    private val pokemonViewModel = PokemonViewModel()
    private val pokemonsListAdapter = PokemonsListAdapter(onLaunchPokemonDescriptionFragment)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val pokemonsRecyclerView = view.findViewById<RecyclerView>(R.id.RecyclerView__pokemonlist)

        pokemonsRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = pokemonsListAdapter
        }

        loadPokemons(view)

    }

    private fun loadPokemons(view: View) {
        val errorTextView = view.findViewById<TextView>(R.id.textView__pokemon_list_error_text)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar__pokemonlist)
        progressBar.visibility = View.VISIBLE
        errorTextView.visibility = View.GONE

        progressBar.visibility = View.VISIBLE
        if (pokemonViewModel.internetConnected(context)) {
            try {
                pokemonViewModel.viewModelScope.launch {
                    pokemonViewModel.pokemonList.collect {
                        progressBar.visibility = View.GONE
                        pokemonsListAdapter.submitList(it)
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
        fun newInstance() = PokemonListFragment()
    }
}