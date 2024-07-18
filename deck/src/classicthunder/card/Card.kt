package classicthunder.card

import classicthunder.character.CharacterStats

abstract class Card(parent: Card?) {

    abstract fun getEnergyCost(): Int
    abstract fun canPlay(stats: CharacterStats): Boolean
    abstract fun play(stats: CharacterStats)

    abstract fun copy(parent: Card): Card
}