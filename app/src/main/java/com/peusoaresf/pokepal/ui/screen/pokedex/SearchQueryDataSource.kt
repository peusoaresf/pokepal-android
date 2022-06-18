package com.peusoaresf.pokepal.ui.screen.pokedex

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class SearchQueryDataSource() {
    private lateinit var searchQueryListener: SearchView.OnQueryTextListener

    fun getSearchQuery(): Flow<String> = callbackFlow {
        searchQueryListener = object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                trySend(query)
                return true
            }
        }

        awaitClose {}
    }

    fun attachSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(searchQueryListener)
    }
}