package classicthunder.card

import java.util.*

class Deck(private var drawPile: List<Card>) {

    private var hand: List<Card>
    private var discardPile: List<Card>
    private val destroyedPile: List<Card>

    private val handSize: Int = 5

    init {
        hand = LinkedList()
        discardPile = LinkedList()
        destroyedPile = LinkedList()
    }

    fun addCard(card: Card) {
        drawPile += card
    }

    fun getDrawPile(): Iterable<Card> {
        return this.drawPile.asIterable()
    }

    fun getHand(): Iterable<Card> {
        return this.hand.asIterable()
    }

    fun getDiscardPile(): Iterable<Card> {
        return this.discardPile.asIterable()
    }

    fun getDestroyedPile(): Iterable<Card> {
        return this.destroyedPile.asIterable()
    }

    fun shuffle() {
        drawPile += discardPile
        discardPile = LinkedList()
        drawPile = drawPile.shuffled()
    }

    fun draw() {
        val overDraw = handSize - drawPile.size
        hand = drawPile.take(handSize)
        drawPile = drawPile.drop(handSize)
        if (overDraw > 0) {
            shuffle()
            hand += drawPile.take(overDraw)
            drawPile = drawPile.drop(overDraw)
        }
    }

    fun discard() {
        discardPile += hand
        hand = LinkedList()
    }

    fun combatClone(): Deck {
        val deckClone = this.drawPile.map{
            it.copy(it)
        }.toMutableList()
        return Deck(deckClone)
    }
}