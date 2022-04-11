package classicthunder.combat.hand

import classicthunder.combat.card.CardActor
import classicthunder.combat.layout.DeckLayout
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

internal class Hand(layout: DeckLayout, cardScale: Float) {
    private val MAX_CARDS = 11
    private val cardsInHand: MutableList<CardActor> = ArrayList(MAX_CARDS)
    private val cardBezier: CardBezier

    init {
        val cardSize = Vector2(CARD_WIDTH, CARD_HEIGHT)
        cardSize.scl(cardScale)
        val cardArea = Rectangle(
            layout.centerWidth - layout.handWidth / 2f, 0.0f,
            layout.handWidth, 200.0f
        )
        cardBezier = CardBezier(cardArea, cardSize)
    }

    // Card Info
    fun addCard(card: CardActor) {
        cardsInHand.add(0, card)
        cardBezier.findRestingPosition(cardsInHand)
    }

    fun getCardCount(): Int {
        return cardsInHand.size
    }

    // Collision Detection
    fun grabCard(mouse: Vector2): CardActor? {
        for (x in cardsInHand.indices.reversed()) {
            val currentCard = cardsInHand[x]
            if (currentCard.containsMouse(mouse)) {
                currentCard.setGrabbed(true)
                currentCard.setDragPosition(mouse)
                return currentCard
            }
        }
        return null
    }

    fun discardHand(discard: Discarder) {
        discard.addCards(cardsInHand)
        cardsInHand.clear()
    }

    fun discardCard(discard: Discarder, card: CardActor) {
        discard.addCard(card)
        cardsInHand.remove(card)
        cardBezier.findRestingPosition(cardsInHand)
    }

    fun resetCards() {
        for (card in cardsInHand) {
            card.reset()
        }
    }

    // LifeCycle
    fun update() {
        for (card in cardsInHand) {
            card.update()
        }
    }

    fun draw(polygonBatch: PolygonSpriteBatch?) {
        for (card in cardsInHand) {
            card.draw(polygonBatch, 1.0f)
        }
    }

    fun draw(batch: SpriteBatch) {}

    companion object {
        const val CARD_WIDTH = 678f
        const val CARD_HEIGHT = 874f
    }
}