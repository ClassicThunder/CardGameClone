package classicthunder.combat

import classicthunder.card.Deck
import classicthunder.character.NonPlayableCharacter
import classicthunder.character.PlayableCharacter

/**
 * A CombatInstance is created for each combat. It makes a copy
 * of the cards that will be destroyed when this combat ends.
 */
class CombatInstance(
    deck: Deck,
    val player: PlayableCharacter,
    val enemy: NonPlayableCharacter
) {

    val combatDeck: Deck = deck.combatClone()

    fun start() {

        combatDeck.shuffle()
        combatDeck.draw()

        enemy.rollIntent()

        // player should play a card here

        enemy.getIntent()

    }

}