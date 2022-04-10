package classicthunder.components.effect

import classicthunder.components.character.CharacterStats

/**
 * Effects represent interactions that "tick". They are applied either at the end or beginning of a turn.
 */
abstract class Effect {
    abstract fun apply(stats: CharacterStats?)
}