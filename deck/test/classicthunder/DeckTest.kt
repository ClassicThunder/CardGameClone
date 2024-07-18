package classicthunder

import classicthunder.card.Deck
import classicthunder.card.impl.DefendCard
import classicthunder.card.impl.StrikeCard
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
}