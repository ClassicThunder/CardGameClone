package classicthunder.combat

import classicthunder.card.Card
import classicthunder.card.Deck
import classicthunder.character.CharacterStats
import classicthunder.character.NonPlayableCharacter
import classicthunder.character.PlayableCharacter

/**
 * A CombatInstance is created for each combat. It makes a copy
 * of the cards that will be destroyed when this combat ends.
 */
class CombatInstance(
    deck: Deck,
    private val player: PlayableCharacter,
    private val enemy: NonPlayableCharacter,
    private var resetEnergy: Int

) {
    val deck: Deck = deck.combatClone()

    var currentEnergy = 0
        private set

    fun start() {

        deck.shuffle()
        deck.draw()

        enemy.rollIntent()
        enemy.getIntent()

        currentEnergy = resetEnergy
    }

    fun canPlayCard(card: Card, target: CharacterStats): Boolean {

        if (card.getEnergyCost() <= currentEnergy) {
            return card.canPlay(target)
        }

        return false;
    }

    fun playCard(card: Card, target: CharacterStats) {

        if (canPlayCard(card, target)) {
            card.play(target)
        }

        currentEnergy -= card.getEnergyCost()
    }

}