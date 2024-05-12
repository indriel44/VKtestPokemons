package com.example.vkpoketestapp.uilayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vkpoketestapp.R
import com.example.vkpoketestapp.uilayer.fragments.PokemonListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.FrameLayout__FragmentHost, PokemonListFragment.newInstance())
            .commit()
    }

}
