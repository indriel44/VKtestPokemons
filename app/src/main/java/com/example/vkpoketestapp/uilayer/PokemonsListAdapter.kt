package com.example.vkpoketestapp.uilayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.vkpoketestapp.R
import com.example.vkpoketestapp.datalayer.Pokemon


class PokemonsListAdapter(
    private val onLaunchPokemonDescriptionFragment: (pokemonId: Int) -> Unit,
) : ListAdapter<Pokemon, PokemonsListAdapter.ViewHolder>(
    DifferentCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon: Pokemon = getItem(position)
        holder.bind(
            pokemon,
            onLaunchPokemonDescriptionFragment,
        )
    }

    private class DifferentCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) =
            oldItem == newItem
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView = view.findViewById<TextView>(R.id.textview__pokemoncard_name)
        private val frontImageView = view.findViewById<ImageView>(R.id.imageview__pokemoncard_front)
        private val backImageView = view.findViewById<ImageView>(R.id.imageview__pokemoncard_back)
        private val card=view.findViewById<LinearLayout>(R.id.linearlayout__pokemoncard_fullcard)

        fun bind(
            pokemon: Pokemon,
            onLaunchPokemonDescriptionFragment: (pokemonId: Int) -> Unit,
        ) {

            nameTextView.text = pokemon.name.replaceFirstChar {
                it.uppercaseChar()
            }
            frontImageView.load(pokemon.sprites.front)
            backImageView.load(pokemon.sprites.back)

            card.setOnClickListener {
                    onLaunchPokemonDescriptionFragment(pokemon.id)
            }

        }


    }


}

