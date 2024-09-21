package classicthunder

import classicthunder.card.Deck
import classicthunder.card.impl.DefendCard
import classicthunder.card.impl.StrikeCard
import classicthunder.character.CharacterStats
import classicthunder.character.CharacterType
import classicthunder.character.PlayableCharacter
import classicthunder.character.impl.StabbyBookPlayableCharacter
import classicthunder.combat.CombatInstance
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DeckTest {

    @Test
    fun deckCycleTest() {

        val deck = Deck(mutableListOf(
            // 6
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            // 4
            DefendCard(null),
            DefendCard(null),
            DefendCard(null),
            DefendCard(null),
        ))

        deck.shuffle()
        deck.draw()

        assertEquals(5, deck.getDrawPile().count())
        assertEquals(5, deck.getHand().count())
        assertEquals(0, deck.getDiscardPile().count())
        assertEquals(0, deck.getDestroyedPile().count())

        deck.discard()

        assertEquals(5, deck.getDrawPile().count())
        assertEquals(0, deck.getHand().count())
        assertEquals(5, deck.getDiscardPile().count())
        assertEquals(0, deck.getDestroyedPile().count())

        deck.draw()

        assertEquals(0, deck.getDrawPile().count())
        assertEquals(5, deck.getHand().count())
        assertEquals(5, deck.getDiscardPile().count())
        assertEquals(0, deck.getDestroyedPile().count())

        deck.discard()

        assertEquals(0, deck.getDrawPile().count())
        assertEquals(0, deck.getHand().count())
        assertEquals(10, deck.getDiscardPile().count())
        assertEquals(0, deck.getDestroyedPile().count())

        deck.draw()

        assertEquals(5, deck.getDrawPile().count())
        assertEquals(5, deck.getHand().count())
        assertEquals(0, deck.getDiscardPile().count())
        assertEquals(0, deck.getDestroyedPile().count())
    }

    @Test
    fun smallDeckTest() {

        val deck = Deck(mutableListOf(
            StrikeCard(null),
            DefendCard(null),
        ))

        deck.shuffle()
        deck.draw()

        assertEquals(0, deck.getDrawPile().count())
        assertEquals(2, deck.getHand().count())
        assertEquals(0, deck.getDiscardPile().count())
        assertEquals(0, deck.getDestroyedPile().count())
    }

    @Test
    fun deckIsCopiedForCombatTest() {

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

        val combat = CombatInstance(deck, player, enemy, 3)
        combat.start()

        assertEquals(5, combat.deck.getHand().count())
        assertEquals(5, combat.deck.getDrawPile().count())
        assertEquals(0, combat.deck.getDiscardPile().count())
    }
}