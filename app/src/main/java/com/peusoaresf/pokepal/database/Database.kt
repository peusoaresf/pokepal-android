package com.peusoaresf.pokepal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peusoaresf.pokepal.database.dao.PokemonDao
import com.peusoaresf.pokepal.database.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokepalDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}

private lateinit var INSTANCE: PokepalDatabase

fun getDatabase(context: Context): PokepalDatabase {
    synchronized(PokepalDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, PokepalDatabase::class.java,"Pokepal")
                .createFromAsset("database/Pokepal.db")
                .build()
        }
    }
    return INSTANCE
}