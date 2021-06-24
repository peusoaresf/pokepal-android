package com.peusoaresf.pokepal.domain

import com.peusoaresf.pokepal.R

enum class PokemonType(val type: String, val resource: Int) {
    BUG("bug", R.drawable.type_bug),
    DARK("dark", R.drawable.type_dark),
    DRAGON("dragon", R.drawable.type_dragon),
    ELECTRIC("electric", R.drawable.type_electric),
    FAIRY("fairy", R.drawable.type_fairy),
    FIGHTING("fighting", R.drawable.type_fighting),
    FIRE("fire", R.drawable.type_fire),
    FLYING("flying", R.drawable.type_flying),
    GHOST("ghost", R.drawable.type_ghost),
    GRASS("grass", R.drawable.type_grass),
    GROUND("ground", R.drawable.type_ground),
    ICE("ice", R.drawable.type_ice),
    NORMAL("normal", R.drawable.type_normal),
    POISON("poison", R.drawable.type_poison),
    PSYCHIC("psychic", R.drawable.type_psychic),
    ROCK("rock", R.drawable.type_rock),
    STEEL("steel", R.drawable.type_steel),
    WATER("water", R.drawable.type_water),
    UNKNOWN("unknown", R.drawable.ic_broken_image);

    companion object {
        fun fromString(type: String?): PokemonType = when (type) {
            BUG.type -> BUG
            DARK.type -> DARK
            DRAGON.type -> DRAGON
            ELECTRIC.type -> ELECTRIC
            FAIRY.type -> FAIRY
            FIGHTING.type -> FIGHTING
            FIRE.type -> FIRE
            FLYING.type -> FLYING
            GHOST.type -> GHOST
            GRASS.type -> GRASS
            GROUND.type -> GROUND
            ICE.type -> ICE
            NORMAL.type -> NORMAL
            POISON.type -> POISON
            PSYCHIC.type -> PSYCHIC
            ROCK.type -> ROCK
            STEEL.type -> STEEL
            WATER.type -> WATER
            else -> UNKNOWN
        }
    }
}