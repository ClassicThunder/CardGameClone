package classicthunder

import classicthunder.card.Deck
import classicthunder.card.impl.DefendCard
import classicthunder.card.impl.StrikeCard
import classicthunder.character.CharacterStats
import classicthunder.character.CharacterType
import classicthunder.character.PlayableCharacter
import classicthunder.character.impl.StabbyBookPlayableCharacter
import classicthunder.combat.CombatInstance
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CombatTest {

    @Test
    fun combatTest() {

        val deck = Deck(mutableListOf(
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            DefendCard(null),
            DefendCard(null),
            DefendCard(null),
            DefendCard(null),
        ))

        val player = PlayableCharacter(CharacterStats(CharacterType.PLAYER, 100))
        val enemy = StabbyBookPlayableCharacter(CharacterStats(CharacterType.ENEMY, 100))

        val combat = CombatInstance(deck, player , enemy)

        combat.combatDeck.addCard(StrikeCard(null))

        // The combat copy shouldn't affect the base deck
        assertEquals(10, deck.getDrawPile().count())


    }
}