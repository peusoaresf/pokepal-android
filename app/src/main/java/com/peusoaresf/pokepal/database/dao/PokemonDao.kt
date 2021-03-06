package com.peusoaresf.pokepal.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peusoaresf.pokepal.database.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Query("""
        select * from table_pokemon 
        where 
            is_default = 1 
            and (
                name like :filter 
                or primary_type like :filter 
                or secondary_type like :filter
            )
    """)
    fun getPokemons(filter: String): LiveData<List<PokemonEntity>>

    @Query("delete from table_pokemon")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokemons: PokemonEntity)
}