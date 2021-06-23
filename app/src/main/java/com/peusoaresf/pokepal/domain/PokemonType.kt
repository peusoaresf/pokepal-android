package com.peusoaresf.pokepal.domain

enum class PokemonType(val type: String) {
    BUG("bug"),
    DARK("dark"),
    DRAGON("dragon"),
    ELECTRIC("electric"),
    FAIRY("fairy"),
    FIGHTING("fighting"),
    FIRE("fire"),
    FLYING("flying"),
    GHOST("ghost"),
    GRASS("grass"),
    GROUND("ground"),
    ICE("ice"),
    NORMAL("normal"),
    POISON("poison"),
    PSYCHIC("psychic"),
    ROCK("rock"),
    WATER("water"),
    UNKNOWN("unknown");

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
            WATER.type -> WATER
            else -> UNKNOWN
        }
    }
}